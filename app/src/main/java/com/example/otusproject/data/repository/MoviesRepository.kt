package com.example.otusproject.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.otusproject.data.api.MovieDbService
import com.example.otusproject.data.database.MovieDao
import com.example.otusproject.data.vo.JsonMovie
import com.example.otusproject.data.vo.JsonResponse
import com.example.otusproject.data.vo.MovieItem
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MoviesRepository (private val movieDao: MovieDao){
    private val _movies = mutableListOf<MovieItem>()
    private val _moviesFavorites = mutableListOf<MovieItem>()
    private val databaseThreadPool : ExecutorService = Executors.newFixedThreadPool(2)


    val movieFavorites: List<MovieItem>
    @SuppressLint("CheckResult")
    get() {
        movieDao.getAllFavorites()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                    _moviesFavorites.clear()
                    _moviesFavorites.addAll(it)
            }
        return _moviesFavorites
    }

    fun getMovies(movieDbService: MovieDbService, context: Context): List<MovieItem> {
        val pref = context.getSharedPreferences("time", Context.MODE_PRIVATE)
        val now = Calendar.getInstance().timeInMillis

        if (pref.contains("time")
            && ((now - pref.getLong("time", now))/60000 < 20) ){
           loadFromRoom()
        } else {
            loadFromApi(movieDbService)
            pref.edit().putLong("time", now).apply()
        }
            Thread.sleep(100) // костыль
            return _movies
    }

    private fun loadFromRoom() {
       databaseThreadPool.execute {
           _movies.addAll(movieDao.getAll())
           Log.d("mTag", movieDao.getAll().size.toString() + "loaded from room")
       }

    }

    private fun loadFromApi(movieDbService: MovieDbService) {
        val temp = mutableListOf<MovieItem>()
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
                        _movies.addAll(temp)
                        insertListRoom(temp)
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

    fun geById(id: Int) : MovieItem {
        var temp : MovieItem? = null
        databaseThreadPool.execute {
            temp = movieDao.getById(id)
        }
        Thread.sleep(100)
        return temp!!
    }

    private fun insertListRoom(movies: List<MovieItem>) {
        Log.d("mTag", movies.size
            .toString()+ " inserted in room")
       databaseThreadPool.execute {
           movieDao.insertAll(movies)
       }

    }

    private fun insertItemRoom(movie: MovieItem) {
            movieDao.insert(movie)
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
}