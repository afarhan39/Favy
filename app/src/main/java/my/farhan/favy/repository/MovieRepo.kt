package my.farhan.favy.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import my.farhan.favy.data.SortMethod
import my.farhan.favy.data.db.Movie
import my.farhan.favy.data.db.MovieDao
import my.farhan.favy.data.network.ApiEvent
import my.farhan.favy.data.network.MovieEndpoint
import my.farhan.favy.data.network.Status
import my.farhan.favy.util.TAG
import my.farhan.favy.util.toEpoch

class MovieRepo(private val api: MovieEndpoint, private val dao: MovieDao) {
    val moviesLD = dao.findAllLD()
    val selectedMovie = MutableLiveData<Movie>()
    val apiEvent = MutableLiveData<ApiEvent>()

    private val moviesNeo = MutableLiveData<List<Movie>>()

    fun getAllMovies(): LiveData<List<Movie>> = moviesNeo

    suspend fun sortBy(sortMethod: SortMethod) {
        val temp = dao.findAll()
        when (sortMethod) {
            SortMethod.Rating -> {
                moviesNeo.postValue(temp.sortedByDescending { it.voteAverage })
            }
            SortMethod.Alphabetical -> {
                moviesNeo.postValue(temp.sortedBy { it.title })
            }
            SortMethod.Popularity -> {
                moviesNeo.postValue(temp.sortedByDescending { it.popularity })
            }
            else -> {
                moviesNeo.postValue(temp.sortedByDescending { it.epochRelease })
            }
        }
    }

    suspend fun discoverMoviesAPI() {
        try {
            val response = api.getDiscoverMovie(
                "328c283cd27bd1877d9080ccb1604c91",
                "2016-12-31",
                "release_date.desc",
                "1"
            )
            if (response.isSuccessful) {
                val list = ArrayList<Movie>()
                for (item in response.body()!!.results) {
                    val backDropUrl =
                        if (item.backdropPath != null) "https://image.tmdb.org/t/p/w780/${item.backdropPath}"
                        else ""
                    val posterUrl =
                        if (item.posterPath != null) "https://image.tmdb.org/t/p/w342/${item.posterPath}"
                        else ""
                    list.add(
                        Movie(
                            item.id,
                            backDropUrl,
                            posterUrl,
                            item.title,
                            item.popularity,
                            item.releaseDate,
                            item.releaseDate.toEpoch(),
                            item.overview,
                            item.voteAverage,
                            item.voteCount
                        )
                    )
                }
                dao.addList(list)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    suspend fun nowPlayingMoviesAPINeo(page: Int) {
        try {
            apiEvent.postValue(ApiEvent(Status.LOADING, ""))
            val response = api.getNowPlayingMovie(
                "328c283cd27bd1877d9080ccb1604c91",
                page.toString()
            )
            if (response.isSuccessful) {
                if (page == 1) dao.deleteAllMovie()
                apiEvent.postValue(ApiEvent(Status.SUCCESS, ""))
                for (item in response.body()!!.results) {
                    val backDropUrl =
                        if (item.backdropPath != null) "https://image.tmdb.org/t/p/w780/${item.backdropPath}"
                        else ""
                    val posterUrl =
                        if (item.posterPath != null) "https://image.tmdb.org/t/p/w342/${item.posterPath}"
                        else ""
                    dao.add(
                        Movie(
                            item.id,
                            backDropUrl,
                            posterUrl,
                            item.title,
                            item.popularity,
                            item.releaseDate,
                            item.releaseDate.toEpoch(),
                            item.overview,
                            item.voteAverage,
                            item.voteCount
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            apiEvent.postValue(ApiEvent(Status.ERROR, e.toString()))
        }
    }

    suspend fun getMovieDetailsAPI(movieId: Int) {
        try {
            apiEvent.postValue(ApiEvent(Status.LOADING, ""))
            val response = api.getMovie(
                movieId.toString(), "328c283cd27bd1877d9080ccb1604c91"
            )
            if (response.isSuccessful) {
                apiEvent.postValue(ApiEvent(Status.SUCCESS, ""))
                val movie = dao.findMovie(movieId)
                movie.hasCalledDetailApi = true
                movie.genre = response.body()!!.genres.map { it.name }
                movie.runTime = response.body()!!.runtime
                selectedMovie.postValue(movie)
                dao.add(movie)
            }
        } catch (e: Exception) {
            apiEvent.postValue(ApiEvent(Status.ERROR, e.toString()))
        }
    }

    suspend fun getMovieDetailsDB(movieId: Int): Movie {
        return dao.findMovie(movieId)
    }
}