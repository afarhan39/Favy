package my.farhan.favy.di

import my.farhan.favy.data.network.MovieEndpoint
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideMoviesApi(retrofit: Retrofit): MovieEndpoint {
        return retrofit.create(MovieEndpoint::class.java)
    }
    single { provideMoviesApi(get()) }
}