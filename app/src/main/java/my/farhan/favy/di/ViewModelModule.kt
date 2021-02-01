package my.farhan.favy.di

import my.farhan.favy.ui.detail.MovieDetailVM
import my.farhan.favy.ui.list.MovieListVM
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieListVM(get()) }
    viewModel { MovieDetailVM(get()) }
}