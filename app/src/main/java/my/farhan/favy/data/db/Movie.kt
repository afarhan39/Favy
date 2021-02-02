package my.farhan.favy.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/***
 * An entity of Movie, combining
 * [my.farhan.favy.data.network.model.MovieListRes] and
 * [my.farhan.favy.data.network.model.MovieDetailRes]
 * Saved into Room
 *
 * [hasCalledDetailApi] is used to distinguish whether to call
 * [my.farhan.favy.repository.MovieRepo.getMovieDetailsAPI] when false
 * and [my.farhan.favy.repository.MovieRepo.getMovieDetailsDB] when true
 *
 * since [backDropUrl] and [posterUrl] is possible to be null from
 * [my.farhan.favy.data.network.model.MovieListRes] and
 * [my.farhan.favy.data.network.model.MovieDetailRes], it will be set as ""
 */
@Entity
data class Movie(
    //list
    @PrimaryKey
    val movieId: Int,
    val backDropUrl: String,
    val posterUrl: String,
    val title: String,
    val popularity: Double,
    val releaseDate: String,
    val epochRelease: Long,
    val overview: String,
    val voteAverage: Double,
    val voteCount: Int,
    //detail
    var hasCalledDetailApi: Boolean = false,
    var genre: List<String>? = emptyList(),
    var runTime: Int? = 0
)