package my.farhan.favy.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

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