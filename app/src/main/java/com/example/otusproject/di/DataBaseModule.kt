package com.example.otusproject.di

import android.app.Application
import androidx.room.Room
import com.example.otusproject.data.database.AppDb
import com.example.otusproject.data.database.MovieDao
import com.example.otusproject.data.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {
    private var instance: AppDb? = null

    @Singleton
    @Provides
    fun provideRoomDatabase(application: Application): AppDb {
        if (instance == null) {
            synchronized(AppDb::class) {
                instance = Room.databaseBuilder(
                    application,
                    AppDb::class.java,
                    "db-movie1.db"
                )
                    .build()
            }
        }
        return instance!!
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDb): MovieDao {
        return db.getMovieDao()
    }

    @Singleton
    @Provides
    fun moviesRepository(movieDao: MovieDao): MoviesRepository {
        return MoviesRepository(movieDao)
    }
}