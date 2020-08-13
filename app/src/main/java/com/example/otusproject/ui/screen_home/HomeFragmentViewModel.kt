package com.example.otusproject.ui.screen_home

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.otusproject.data.App
import com.example.otusproject.data.repository.MovieDbUseCase
import com.example.otusproject.data.vo.MovieItem

class HomeFragmentViewModel: ViewModel() {
    private val moviesLiveData = MutableLiveData<List<MovieItem>>()
    private val errorLiveData = MutableLiveData<String>()
    private val selectedMovieLiveData = MutableLiveData<MovieItem>()
    private val favoriteLiveData = MutableLiveData<MutableList<MovieItem>>()

    private val movieDbUseCase = App.instance.useCase

    val movies: LiveData<List<MovieItem>>
        get() = moviesLiveData

    val error : LiveData<String>
        get() = errorLiveData

    val selectedMovie : LiveData<MovieItem>
        get() = selectedMovieLiveData

    val favorites : LiveData<MutableList<MovieItem>>
        get() = favoriteLiveData

    fun initMovieList() {
        movieDbUseCase.getMovies(object: MovieDbUseCase.GetMoviesCallback {
            override fun onSuccess(movies: List<MovieItem>) {
                moviesLiveData.postValue(movies)
            }

            override fun onError(error: String) {
                errorLiveData.postValue(error)
            }
        })
    }

    fun initFavList() {
        movieDbUseCase.getFavorites(object : MovieDbUseCase.GetFavMoviesCallback {
            override fun onSuccess(movies: MutableList<MovieItem>) {
                favoriteLiveData.postValue(movies)
            }
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

    fun showDetailsFromNotification(id: Int) {
        selectedMovieLiveData.postValue(movieDbUseCase.getById(id))
    }
}