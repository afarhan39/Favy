package my.farhan.favy.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import my.farhan.favy.data.db.Movie
import my.farhan.favy.data.db.MovieDao
import my.farhan.favy.data.network.ApiEvent
import my.farhan.favy.data.network.MovieEndpoint
import my.farhan.favy.data.network.Status
import my.farhan.favy.util.TAG

class MovieRepo(private val api: MovieEndpoint, private val dao: MovieDao) {
    val moviesLD = dao.findAllLD()
    val selectedMovie = MutableLiveData<Movie>()
    val apiEvent = MutableLiveData<ApiEvent>()

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
                            item.releaseDate
                        )
                    )
                }
                dao.addList(list)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    suspend fun nowPlayingMoviesAPI(page: Int) {
        try {
            apiEvent.postValue(ApiEvent(Status.LOADING, ""))
            val response = api.getNowPlayingMovie(
                "328c283cd27bd1877d9080ccb1604c91",
                page.toString()
            )
            if (response.isSuccessful) {
                apiEvent.postValue(ApiEvent(Status.SUCCESS, ""))
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
                            item.releaseDate
                        )
                    )
                }
                dao.addList(list)
            }
        } catch (e: Exception) {
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
                movie.overview = response.body()!!.overview
                movie.voteAverage = response.body()!!.voteAverage
                movie.voteCount = response.body()!!.voteCount
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