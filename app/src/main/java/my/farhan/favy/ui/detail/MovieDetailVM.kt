package my.farhan.favy.ui.detail

import androidx.lifecycle.ViewModel
import my.farhan.favy.repository.MovieRepo

class MovieDetailVM(private val movieRepo: MovieRepo) : ViewModel() {
    val movieList = movieRepo.moviesLD
    val selectedMovie = movieRepo.selectedMovie
}