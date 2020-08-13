package com.example.otusproject.data.repository

import com.example.otusproject.data.api.MovieDbService
import com.example.otusproject.data.vo.Movie
import com.example.otusproject.data.vo.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MovieDBInteractor(private val movieDbService: MovieDbService, private val moviesRepository: MoviesRepository) {

    fun getMovies(callback: GetMoviesCallback) {
        if (moviesRepository.cachedMovies.isNotEmpty()) {
            callback.onSuccess(moviesRepository.cachedMovies)
            return
        }
        movieDbService.getMoviesFromDB().enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.apply {
                        moviesRepository.addToCache(this.movies)
                    }
                    callback.onSuccess(moviesRepository.cachedMovies)
                } else {
                    callback.onError(response.code().toString()+"")
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                callback.onError("network problems")
            }
        })
    }
    interface GetMoviesCallback {
        fun onSuccess(movies: List<Movie>)
        fun onError(error: String)
    }
}