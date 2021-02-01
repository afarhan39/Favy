package my.farhan.favy.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    //list
    val movieId: Int,
    val backDropUrl: String,
    val posterUrl: String,
    val title: String,
    val popularity: Double,
    val releaseDate: String,
    //detail
    var hasCalledDetailApi: Boolean = false,
    var genre: List<String>? = emptyList(),
    var overview: String? = "",
    var voteAverage: Double? = 0.0,
    var voteCount: Int? = 0,
    var runTime: Int? = 0,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)