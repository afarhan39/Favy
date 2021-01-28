package my.farhan.movie.repository

import my.farhan.movie.data.network.MovieEndpoint

class MovieRepo(private val api: MovieEndpoint) {

    suspend fun getAllMovies(): List<String> {
        return emptyList()
    }
}