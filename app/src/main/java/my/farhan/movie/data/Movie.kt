package my.farhan.movie.data

data class Movie(
    //list
    val movieId: String,
    val backDropImg: String,
    val genre: List<String>,
    val title: String,
    val popularity: String,
    //detail
    var overview: String,
    var voteAverage: Double,
    var voteCount: Int,
    var runTime: Int

)
