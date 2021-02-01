package my.farhan.favy.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.farhan.favy.data.SortMethod
import my.farhan.favy.repository.MovieRepo

class MovieListVM(private val movieRepo: MovieRepo) : ViewModel() {
    val selectedMovie = movieRepo.selectedMovie
    val sortOptionList = SortMethod.values().map { it.label }
    val selectedSortOption = MutableLiveData(SortMethod.ReleaseDate.label)
    private val currentPage = MutableLiveData(1)
    val apiEvent = movieRepo.apiEvent

    val moviesNeo = movieRepo.getAllMovies()

    fun sortBy(sortMethod: SortMethod) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedSortOption.postValue(sortMethod.label)
            movieRepo.sortBy(sortMethod)
        }
    }

    fun onRefreshMovies() {
        currentPage.value = 1
        viewModelScope.launch(Dispatchers.IO) {
            movieRepo.nowPlayingMoviesAPINeo(1)
        }
    }

    fun onLoadMoreMovies() {
        val nextPage = currentPage.value!! + 1
        currentPage.value = nextPage
        viewModelScope.launch(Dispatchers.IO) {
            movieRepo.nowPlayingMoviesAPINeo(nextPage)
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movie = movieRepo.getMovieDetailsDB(movieId)
            if (movie.hasCalledDetailApi)
                movieRepo.selectedMovie.postValue(movie)
            else
                movieRepo.getMovieDetailsAPI(movieId)
        }
    }
}