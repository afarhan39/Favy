package my.farhan.movie.di

import android.app.Application
import androidx.room.Room
import my.farhan.movie.data.db.MovieDao
import my.farhan.movie.data.db.MovieDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): MovieDatabase {
        return Room.databaseBuilder(application, MovieDatabase::class.java, "countries")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideMovieDao(get()) }
}