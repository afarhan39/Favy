package my.farhan.movie.di

import my.farhan.movie.data.network.MovieEndpoint
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideCountriesApi(retrofit: Retrofit): MovieEndpoint {
        return retrofit.create(MovieEndpoint::class.java)
    }
    single { provideCountriesApi(get()) }
}