package my.farhan.movie.di

import my.farhan.movie.ui.detail.MovieDetailVM
import my.farhan.movie.ui.list.MovieListVM
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieDetailVM() }
    viewModel { MovieListVM() }
}