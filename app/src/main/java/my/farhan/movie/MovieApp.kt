package my.farhan.movie

import android.app.Application
import my.farhan.movie.di.apiModule
import my.farhan.movie.di.networkModule
import my.farhan.movie.di.repositoryModule
import my.farhan.movie.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MovieApp)
            modules(apiModule + viewModelModule + repositoryModule + networkModule)
        }
    }
}