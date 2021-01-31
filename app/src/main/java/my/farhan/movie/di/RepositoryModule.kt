package my.farhan.movie.di

import my.farhan.movie.repository.MovieRepo
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepo(get(), get()) }
}