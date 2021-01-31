package my.farhan.movie.repository

import android.util.Log
import my.farhan.movie.data.db.Movie
import my.farhan.movie.data.db.MovieDao
import my.farhan.movie.data.network.MovieEndpoint
import my.farhan.movie.util.TAG

class MovieRepo(private val api: MovieEndpoint, private val dao: MovieDao) {
    val moviesLD = dao.findAllLD()
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
                    val bgImg = item.backdropPath ?: item.posterPath
                    ?: "https://smithssanitationsupply.ca/wp-content/uploads/2018/06/noimage-1.png"
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