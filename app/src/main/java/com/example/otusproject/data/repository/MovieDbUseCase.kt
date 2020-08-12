package com.example.otusproject.data.repository

import android.content.Context
import com.example.otusproject.data.api.MovieDbService
import com.example.otusproject.data.vo.MovieItem
import io.reactivex.Single

class MovieDbUseCase(
    private val context: Context,
    private val movieDbService: MovieDbService,
    private val moviesRepository: MoviesRepository
)  {
    
    fun getMovies(callback: GetMoviesCallback) {
        moviesRepository.getMovies(movieDbService, context, object : MoviesRepository.OnListReturn {
            override fun onResponse(movies: Single<List<MovieItem>>) {
                callback.onSuccess(movies)
            }

            override fun onResponseFailed(error: String) {
               callback.onError(error)
            }
        })
    }

    fun getFavorites(): Single<List<MovieItem>> {
       return moviesRepository.movieFavorites
    }

    fun refreshItem(movie: MovieItem) {
        moviesRepository.refreshItem(movie)
    }

    fun removeMovieFromFav(movie: MovieItem) {
        movie.isFavorite = false
        moviesRepository.refreshItem(movie)
    }

    fun getById(id: Int): Single<MovieItem>{
        return moviesRepository.geById(id)
    }

    interface GetMoviesCallback {
        fun onSuccess(movies: Single<List<MovieItem>>)
        fun onError(error: String)
    }
}