package my.farhan.movie.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.farhan.movie.data.db.Movie
import my.farhan.movie.repository.MovieRepo
import my.farhan.movie.util.TAG

class MovieListVM(private val movieRepo: MovieRepo) : ViewModel() {
    val movieList = movieRepo.moviesLD
    val selectedMovie = movieRepo.selectedMovie

    fun onLoadMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepo.discoverMoviesAPI()
            Log.d(TAG, "aaaa")
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movieRepo.getMovieDetailsDB(movieId).genre.isNullOrEmpty())
                movieRepo.getMovieDetailsAPI(movieId)
            else
                movieRepo.selectedMovie.postValue(movieRepo.getMovieDetailsDB(movieId))
        }
    }
}