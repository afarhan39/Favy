package my.farhan.favy.data.network

import my.farhan.favy.data.network.model.MovieDetailRes
import my.farhan.favy.data.network.model.MovieListRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieEndpoint {

    @GET("discover/movie")
    suspend fun getDiscoverMovie(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.lte") releaseDateLTE: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: String
    )
            : Response<MovieListRes>

    @GET("movie/{movieId}")
    suspend fun getMovie(@Path("movieId") movieId: String, @Query("api_key") apiKey: String)
            : Response<MovieDetailRes>
}