package com.example.otusproject.data.repository

import android.content.Context
import android.os.Build
import com.example.otusproject.data.api.MovieDBClient
import com.example.otusproject.data.api.MovieDbService
import com.example.otusproject.data.vo.Movie
import com.example.otusproject.data.vo.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MovieDBInteractor(private val context: Context, private val movieDbService: MovieDbService, private val moviesRepository: MoviesRepository) {
    val databaseWriteExecutor : ExecutorService = Executors.newFixedThreadPool(2);

    fun getMovies(callback: GetMoviesCallback) {
        val roomDatabase = MovieDBClient.instance.roomDb
        val pref = context.getSharedPreferences("time", Context.MODE_PRIVATE)

        if(pref.contains("time")
            && ((Calendar.getInstance().timeInMillis - pref.getLong("time", Calendar.getInstance().timeInMillis))/60000 < 20)){
            databaseWriteExecutor.execute {
                roomDatabase?.getMovieDao()?.getAll()?.let { callback.onSuccess(it) }
            }
            return
        }

        movieDbService.getMoviesFromDB().enqueue(object : Callback<MovieResponse>{

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponse  = mutableListOf<Movie>()
                if (response.isSuccessful) {
                    response.body()?.apply {
                        movieResponse.addAll(this.movies)
                    }

                    databaseWriteExecutor.execute {
//                        roomDatabase?.getMovieDao()?.insertMovies(movieResponse)
                        pref.edit().putLong("time", Calendar.getInstance().timeInMillis).apply()
                        roomDatabase?.getMovieDao()?.getAll()?.let { callback.onSuccess(it) }
                    }
                    
                } else { callback.onError(response.code().toString()+"") }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                databaseWriteExecutor.execute {
                    if (!roomDatabase?.getMovieDao()?.getAll().isNullOrEmpty()) {
                        roomDatabase?.getMovieDao()?.getAll()?.let { callback.onSuccess(it) }
                    } else { callback.onError("network problems") }
                }
            }
        })
    }

    fun getFavMovies(callback: GetFavMoviesCallback) {
        val roomDatabaseFav = MovieDBClient.instance.roomDbFav
        databaseWriteExecutor.execute {
            roomDatabaseFav?.getMovieDao()?.getAllFavorites()?.let { callback.getFavMovies(it as MutableList<Movie>) }
        }
    }

    fun putMovieToFav(movie: Movie) {
        val roomDatabaseFav = MovieDBClient.instance.roomDbFav
        databaseWriteExecutor.execute {
            roomDatabaseFav?.getMovieDao()?.insertFavorite(movie)
        }
    }

    fun removeMovieFromFav(movie: Movie) {
        val roomDatabaseFav = MovieDBClient.instance.roomDbFav
        databaseWriteExecutor.execute {
            roomDatabaseFav?.getMovieDao()?.deleteFromFavorite(movie)
        }
    }

    fun getMovieById (id: Int) : Movie? {
        var movie1 : Movie? = null
        val roomDatabase = MovieDBClient.instance.roomDb
        databaseWriteExecutor.execute {
            roomDatabase?.getMovieDao()?.getAll()?.let {list ->
                list.forEach { movie ->
                    if (movie.id == id)
                        movie1 = movie
                }
            }
        }
        return movie1
    }

    interface GetMoviesCallback {
        fun onSuccess(movies: List<Movie>)
        fun onError(error: String)
    }

    interface GetFavMoviesCallback {
        fun getFavMovies(movies: MutableList<Movie>)
    }
    interface GetMovieById {
       fun getMovieById(movie: Movie)
    }
}