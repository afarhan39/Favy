package my.farhan.movie.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import my.farhan.movie.data.db.Movie
import my.farhan.movie.data.db.MovieDao
import my.farhan.movie.data.network.MovieEndpoint
import my.farhan.movie.util.TAG

class MovieRepo(private val api: MovieEndpoint, private val dao: MovieDao) {
    val moviesLD = dao.findAllLD()
    val selectedMovie = MutableLiveData<Movie>()

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
                    val bgImg = when {
                        item.backdropPath != null -> "https://image.tmdb.org/t/p/w300/${item.backdropPath}"
                        item.posterPath != null -> "https://image.tmdb.org/t/p/w342/${item.posterPath}"
                        else -> ""
                    }
                    list.add(Movie(item.id, bgImg, item.title, item.popularity))
                }
                dao.addList(list)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    suspend fun getMovieDetailsAPI(movieId: Int) {
        try {
            val response = api.getMovie(
                movieId.toString(), "328c283cd27bd1877d9080ccb1604c91"
            )
            if (response.isSuccessful) {
                val movie = dao.findMovie(movieId)
                movie.hasCalledDetailApi = true
                movie.genre = response.body()?.genres?.map { it.name }
                movie.overview = response.body()?.overview
                movie.voteAverage = response.body()?.voteAverage
                movie.voteCount = response.body()?.voteCount
                movie.runTime = response.body()?.runtime
                selectedMovie.postValue(movie)
                dao.add(movie)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    suspend fun getMovieDetailsDB(movieId: Int): Movie {
        return dao.findMovie(movieId)
    }
}