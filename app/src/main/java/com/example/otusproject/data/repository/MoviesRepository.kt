package com.example.otusproject.data.repository

import android.content.Context
import com.example.otusproject.data.api.MovieDbService
import com.example.otusproject.data.database.MovieDao
import com.example.otusproject.data.vo.JsonMovie
import com.example.otusproject.data.vo.JsonResponse
import com.example.otusproject.data.vo.MovieItem
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MoviesRepository (private val movieDao: MovieDao){
    private val databaseThreadPool : ExecutorService = Executors.newFixedThreadPool(2)

    val movieFavorites: Single<List<MovieItem>>
    get() {
        return movieDao.getAllFavorites()
    }

    fun getMovies(movieDbService: MovieDbService, context: Context, callback: OnListReturn) {
        val pref = context.getSharedPreferences("time", Context.MODE_PRIVATE)
        val now = Calendar.getInstance().timeInMillis
        val temp = mutableListOf<MovieItem>()

        if (pref.contains("time")
            && ((now - pref.getLong("time", now))/60000 < 20) ){
            callback.onResponse(movieDao.getAll())
            return
        }
            movieDbService.getMoviesFromDB().enqueue(object : Callback<JsonResponse> {
                override fun onResponse(
                    call: Call<JsonResponse>,
                    response: Response<JsonResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.apply {
                            this.jsonMovies.forEach {
                                val movieItem = convertToMovieItem(it)
                                temp.add(movieItem)
                            }
                            pref.edit().putLong("time", now).apply()
                            insertListRoom(temp)
                            if (temp.isEmpty()) {
                                callback.onResponseFailed("Error occurred :(")
                            } else {
                                callback.onResponse(Single.create {
                                    it.onSuccess(temp)
                                })
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
                }
            })
    }

    fun refreshItem(movie: MovieItem) {
        databaseThreadPool.execute {
            movieDao.updateMovie(movie)
        }
    }

    fun geById(id: Int): Single<MovieItem>{
        return movieDao.getById(id)
    }

    private fun insertListRoom(movies: List<MovieItem>) {
       databaseThreadPool.execute {
           movieDao.insertAll(movies)
       }

    }

    private fun convertToMovieItem (jsonMovie: JsonMovie) : MovieItem {
        return MovieItem (
            jsonMovie.adult,
            jsonMovie.backdropPath,
            jsonMovie.id,
            jsonMovie.originalLanguage,
            jsonMovie.originalTitle,
            jsonMovie.overview,
            jsonMovie.popularity,
            jsonMovie.posterPath,
            jsonMovie.releaseDate,
            jsonMovie.title,
            jsonMovie.video,
            jsonMovie.voteAverage,
            jsonMovie.voteCount
        )
    }

    interface OnListReturn {
        fun onResponse (movies: Single<List<MovieItem>>)
        fun onResponseFailed (error: String)

    }
}