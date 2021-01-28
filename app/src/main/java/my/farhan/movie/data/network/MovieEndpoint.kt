package my.farhan.movie.data.network

import retrofit2.Response
import retrofit2.http.GET

interface MovieEndpoint {

    @GET("/api/v1")
    suspend fun getAllCountries(): Response<String>
}