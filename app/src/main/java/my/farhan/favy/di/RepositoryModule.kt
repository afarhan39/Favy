package my.farhan.favy.di

import my.farhan.favy.repository.MovieRepo
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepo(get(), get()) }
}