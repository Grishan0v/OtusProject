package com.example.otusproject.ui.screen_home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.otusproject.data.App
import com.example.otusproject.data.repository.MovieDbUseCase
import com.example.otusproject.data.vo.MovieItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class HomeFragmentViewModel(private val movieDbUseCase: MovieDbUseCase): ViewModel() {
    private val moviesLiveData = MutableLiveData<List<MovieItem>>()
    private val errorLiveData = MutableLiveData<String>()
    private val selectedMovieLiveData = MutableLiveData<MovieItem>()
    private val favoriteLiveData = MutableLiveData<MutableList<MovieItem>>()

    val movies: LiveData<List<MovieItem>>
        get() = moviesLiveData

    val error : LiveData<String>
        get() = errorLiveData

    val selectedMovie : LiveData<MovieItem>
        get() = selectedMovieLiveData

    val favorites : LiveData<MutableList<MovieItem>>
        get() = favoriteLiveData

    @SuppressLint("CheckResult")
    fun initMovieList() {
        movieDbUseCase.getMovies(object: MovieDbUseCase.GetMoviesCallback {
            override fun onSuccess(movies: Single<List<MovieItem>>) {
                movies.subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ moviesLiveData.postValue(it) },
                        { errorLiveData.postValue("Error occurred :(") }
                    )
            }
            override fun onError(error: String) {
                errorLiveData.postValue(error)
            }
        })
    }

    @SuppressLint("CheckResult")
    fun initFavList() {
        movieDbUseCase.getFavorites()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                favoriteLiveData.postValue(it as MutableList<MovieItem>)
            },
        {

        })
    }

    fun onMovieSelect(selectedMovie: MovieItem) {
        selectedMovieLiveData.postValue(selectedMovie)
    }

    fun addToFavorites(movie: MovieItem) {
        movie.isFavorite = true
        movieDbUseCase.refreshItem(movie)
    }

    fun onThemeChange() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    fun removeItemFromFavorites(movie: MovieItem) {
        movieDbUseCase.removeMovieFromFav(movie)
    }

    @SuppressLint("CheckResult")
    fun showDetailsFromNotification(id: Int) {
       movieDbUseCase.getById(id)
           .subscribeOn(Schedulers.computation())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({
               selectedMovieLiveData.postValue(it)
           },{

           })
    }
}