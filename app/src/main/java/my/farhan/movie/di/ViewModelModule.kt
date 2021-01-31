package my.farhan.movie.di

import my.farhan.movie.ui.MovieVM
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieVM(get()) }
}