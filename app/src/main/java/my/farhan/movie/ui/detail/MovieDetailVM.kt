package my.farhan.movie.ui.detail

import androidx.lifecycle.ViewModel
import my.farhan.movie.repository.MovieRepo

class MovieDetailVM(private val movieRepo: MovieRepo) : ViewModel() {
    val movieList = movieRepo.moviesLD
    val selectedMovie = movieRepo.selectedMovie
}