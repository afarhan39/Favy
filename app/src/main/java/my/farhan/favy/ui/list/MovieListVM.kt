package my.farhan.favy.ui.list

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.farhan.favy.data.db.Movie
import my.farhan.favy.repository.MovieRepo

class MovieListVM(private val movieRepo: MovieRepo) : ViewModel() {
    private val movieList = movieRepo.moviesLD
    val selectedMovie = movieRepo.selectedMovie
    val sortOptionList = listOf("Release Date", "Alphabetical", "Rating")
    val selectedSortOption = MutableLiveData(sortOptionList.first())
    private val currentPage = MutableLiveData(1)
    val apiEvent = movieRepo.apiEvent

    fun onRefreshMovies() {
        currentPage.value = 1
        viewModelScope.launch(Dispatchers.IO) {
            movieRepo.nowPlayingMoviesAPI(1)
        }
    }

    fun onLoadMoreMovies() {
        val nextPage = currentPage.value!! + 1
        currentPage.value = nextPage
        viewModelScope.launch(Dispatchers.IO) {
            movieRepo.nowPlayingMoviesAPI(nextPage)
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


    val movieListSorted: LiveData<List<Movie>> = when(selectedSortOption.value) {
        "Alphabetical" -> {
            Transformations.map(movieList) {
                it.sortedByDescending { movie -> movie.title }
            }
        }
        "Rating" -> {
            Transformations.map(movieList) {
                it.sortedByDescending { movie -> movie.voteAverage }
            }
        }
        else -> {
            Transformations.map(movieList) {
                it.sortedByDescending { movie -> movie.epochRelease }
            }
        }
    }
}