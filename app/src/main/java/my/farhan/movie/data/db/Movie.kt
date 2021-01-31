package my.farhan.movie.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    //list
    @PrimaryKey
    val movieId: Int,
    val backDropImg: String,
    val title: String,
    val popularity: Double,
    //detail
    var hasCalledDetailApi: Boolean = false,
    var genre: List<String>? = emptyList(),
    var overview: String? = "",
    var voteAverage: Double? = 0.0,
    var voteCount: Int? = 0,
    var runTime: Int? = 0
)