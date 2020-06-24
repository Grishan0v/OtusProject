package com.example.otusproject.data.repository

import com.example.otusproject.data.database.movies_db.MovieDao
import com.example.otusproject.data.vo.Movie
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MoviesRepository (private val movieDao: MovieDao){
    private val databaseWriteExecutor : ExecutorService = Executors.newFixedThreadPool(2)
    private val _movies = mutableListOf<Movie>()

    val movies : List<Movie>
    get() {
        getMoviesFromDb()
        return _movies
    }


    private fun getMoviesFromDb() {
        databaseWriteExecutor.execute {
            _movies.clear()
            _movies.addAll(movieDao.getAll())
        }
    }

    fun putMoviesInDb(movies: List<Movie>) {
        databaseWriteExecutor.execute {
            movieDao.insertMovies(movies)
        }
    }

    fun clearAllData() {
        movieDao.nukeAll()
    }
}