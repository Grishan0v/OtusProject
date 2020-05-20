package com.example.otusproject.data.repository

import com.example.otusproject.data.vo.Movie

class MoviesRepository {
    private val _cachedMovies = mutableListOf<Movie>()
    private val stockMovies = mutableListOf<Movie>()

    val cachedMovies: List<Movie>
        get() = if (_cachedMovies.size > 0)
            _cachedMovies
        else
            stockMovies

    fun addToCache(movies: List<Movie>) {
        this._cachedMovies.addAll(movies)
    }


}