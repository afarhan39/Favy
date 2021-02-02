package my.farhan.favy.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.farhan.favy.data.SortMethod
import my.farhan.favy.repository.MovieRepo

class MovieListVM(private val movieRepo: MovieRepo) : ViewModel() {
    val sortOptionList = SortMethod.values().map { it.label }
    val selectedSortOption = MutableLiveData(SortMethod.ReleaseDate.label)
    private val currentPage = MutableLiveData(1)
    val apiEvent = movieRepo.getApiEvent()
    val movies = movieRepo.getAllMovies()
    val selectedMovie = movieRepo.getSelectedMovie()

    fun sortBy(sortMethod: SortMethod) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedSortOption.postValue(sortMethod.label)
            movieRepo.sortBy(sortMethod)
        }
    }

    fun onRefreshMovies() {
        currentPage.value = 1
        viewModelScope.launch(Dispatchers.IO) {
            movieRepo.nowPlayingMoviesAPI(1)
            sortBy(SortMethod.ReleaseDate)
        }
    }

    fun onLoadMoreMovies() {
        val nextPage = (currentPage.value ?: 0) + 1
        currentPage.value = nextPage
        viewModelScope.launch(Dispatchers.IO) {
            movieRepo.nowPlayingMoviesAPI(nextPage)
            sortBy(SortMethod.fromLabel(selectedSortOption.value ?: SortMethod.ReleaseDate.label))
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepo.getMovieDetails(movieId)
        }
    }
}