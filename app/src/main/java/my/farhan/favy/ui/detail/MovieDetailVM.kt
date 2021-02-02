package my.farhan.favy.ui.detail

import androidx.lifecycle.ViewModel
import my.farhan.favy.databinding.ActivityMovieDetailBinding
import my.farhan.favy.repository.MovieRepo

class MovieDetailVM(private val movieRepo: MovieRepo) : ViewModel() {
    /***
     * [selectedMovie] will reflect from what [MovieRepo._selectedMovie] set and
     * UI will update accordingly
     */
    val selectedMovie = movieRepo.getSelectedMovie()
}