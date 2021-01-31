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

    suspend fun discoverMovies() {
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
                dao.add(list)
            }
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }

    suspend fun getMovieDetails() {
        try {

        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }
}