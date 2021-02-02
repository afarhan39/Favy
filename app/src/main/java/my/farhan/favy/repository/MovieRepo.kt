package my.farhan.favy.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import my.farhan.favy.data.SortMethod
import my.farhan.favy.data.db.Movie
import my.farhan.favy.data.db.MovieDao
import my.farhan.favy.data.network.ApiEvent
import my.farhan.favy.data.network.MovieEndpoint
import my.farhan.favy.data.network.Status
import my.farhan.favy.util.TAG
import my.farhan.favy.util.toEpoch

/***
 * A repo to hold all API and DB transactions
 */
class MovieRepo(private val api: MovieEndpoint, private val dao: MovieDao) {
    /***
     * _ prefix to mark it is private, and mutable
     * these data will only will mutable inside [MovieRepo] and will be set as LiveData when exposed to ViewModel
     * such as [my.farhan.favy.ui.list.MovieListVM.apiEvent]
     * and [my.farhan.favy.ui.detail.MovieDetailVM.selectedMovie]
     */
    private val _apiEvent = MutableLiveData<ApiEvent>()
    private val _movies = MutableLiveData<List<Movie>>()
    private val _selectedMovie = MutableLiveData<Movie>()

    fun getApiEvent(): LiveData<ApiEvent> = _apiEvent
    fun getAllMovies(): LiveData<List<Movie>> = _movies
    fun getSelectedMovie(): LiveData<Movie> = _selectedMovie

    /***
     * take [sortMethod] params and postValue according to [sortMethod] given
     * by default, will set to [SortMethod.ReleaseDate]
     */
    suspend fun sortBy(sortMethod: SortMethod) {
        val temp = dao.findAll()
        when (sortMethod) {
            SortMethod.Rating -> {
                _movies.postValue(temp.sortedByDescending { it.voteAverage })
            }
            SortMethod.Alphabetical -> {
                _movies.postValue(temp.sortedBy { it.title })
            }
            SortMethod.Popularity -> {
                _movies.postValue(temp.sortedByDescending { it.popularity })
            }
            else -> {
                _movies.postValue(temp.sortedByDescending { it.epochRelease })
            }
        }
    }

    /***
     * call [MovieEndpoint.getNowPlayingMovie] and pass [page]
     * if [page] == 1, it will [MovieDao.deleteAllMovie] first, to ensure copies is fresh
     * if [page] > 1, it will keep appending
     */
    suspend fun nowPlayingMoviesAPI(page: Int) {
        try {
            _apiEvent.postValue(ApiEvent(Status.LOADING, ""))
            val response = api.getNowPlayingMovie(
                "328c283cd27bd1877d9080ccb1604c91",
                page.toString()
            )
            if (response.isSuccessful) {
                if (page == 1) dao.deleteAllMovie()
                for (item in response.body()!!.results) {
                    val backDropUrl =
                        if (item.backdropPath != null) "https://image.tmdb.org/t/p/w780/${item.backdropPath}"
                        else ""
                    val posterUrl =
                        if (item.posterPath != null) "https://image.tmdb.org/t/p/w342/${item.posterPath}"
                        else ""
                    dao.add(
                        Movie(
                            item.id,
                            backDropUrl,
                            posterUrl,
                            item.title,
                            item.popularity,
                            item.releaseDate,
                            item.releaseDate.toEpoch(),
                            item.overview,
                            item.voteAverage,
                            item.voteCount
                        )
                    )
                }
                _apiEvent.postValue(ApiEvent(Status.SUCCESS, ""))
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            _apiEvent.postValue(ApiEvent(Status.ERROR, e.toString()))
        }
    }

    /***
     * distinguish whether to call API or just fetch from DB if [Movie.hasCalledDetailApi]
     */
    suspend fun getMovieDetails(movieId: Int) {
        val movie = getMovieDetailsDB(movieId)
        if (movie.hasCalledDetailApi)
            _selectedMovie.postValue(movie)
        else
            getMovieDetailsAPI(movieId)
    }

    private suspend fun getMovieDetailsAPI(movieId: Int) {
        try {
            _apiEvent.postValue(ApiEvent(Status.LOADING, ""))
            val response = api.getMovie(
                movieId.toString(), "328c283cd27bd1877d9080ccb1604c91"
            )
            if (response.isSuccessful) {
                val movie = dao.findMovie(movieId)
                movie.hasCalledDetailApi = true
                movie.genre = response.body()!!.genres.map { it.name }
                movie.runTime = response.body()!!.runtime
                dao.add(movie)
                _apiEvent.postValue(ApiEvent(Status.SUCCESS, "detail"))
                _selectedMovie.postValue(movie)
            }
        } catch (e: Exception) {
            _apiEvent.postValue(ApiEvent(Status.ERROR, e.toString()))
        }
    }

    private suspend fun getMovieDetailsDB(movieId: Int): Movie {
        return dao.findMovie(movieId)
    }
}