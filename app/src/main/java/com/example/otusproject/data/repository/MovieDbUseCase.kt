package com.example.otusproject.data.repository

import android.content.Context
import android.util.Log
import com.example.otusproject.data.api.MovieDbService
import com.example.otusproject.data.vo.JsonMovie
import com.example.otusproject.data.vo.MovieItem
import com.example.otusproject.data.vo.JsonResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MovieDbUseCase(
    private val context: Context,
    private val movieDbService: MovieDbService,
    private val moviesRepository: MoviesRepository
)  {

    fun getMovies(callback: GetMoviesCallback) {
        moviesRepository.getMovies(movieDbService, context, object : MoviesRepository.OnListReturn {
            override fun returnSuccess(movies: List<MovieItem>) {
                if (movies.isNullOrEmpty()) {
                    callback.onError("Oops, probably network problems")
                } else {
                    callback.onSuccess(movies)
                }
            }
        })
    }

    fun getFavorites(callback: GetFavMoviesCallback) {
        callback.onSuccess(moviesRepository.movieFavorites as MutableList<MovieItem>)
    }

    fun refreshItem(movie: MovieItem) {
        moviesRepository.refreshItem(movie)
    }

    fun removeMovieFromFav(movie: MovieItem) {
        movie.isFavorite = false
        moviesRepository.refreshItem(movie)
    }

    fun getById(id: Int): MovieItem {
        return moviesRepository.geById(id)
    }

    interface GetMoviesCallback {
        fun onSuccess(movies: List<MovieItem>)
        fun onError(error: String)
    }

    interface GetFavMoviesCallback {
        fun onSuccess(movies: MutableList<MovieItem>)
    }
}