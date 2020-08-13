package com.example.otusproject.ui.screen_home

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.otusproject.data.api.MovieDBClient
import com.example.otusproject.data.repository.MovieDBInteractor
import com.example.otusproject.data.vo.Movie

class HomeFragmentViewModel: ViewModel() {
    private val moviesLiveData = MutableLiveData<List<Movie>>()
    private val errorLiveData = MutableLiveData<String>()
    private val selectedMovieLiveData = MutableLiveData<Movie>()
    private val favoriteLiveData = MutableLiveData<List<Movie>>()
    private var favList = mutableListOf<Movie>() // где хранить эту переменную?

    private val movieDBInteractor = MovieDBClient.instance.interactor

    val movies: LiveData<List<Movie>>
        get() = moviesLiveData

    val error : LiveData<String>
        get() = errorLiveData

    val selectedMovie : LiveData<Movie>
        get() = selectedMovieLiveData

    val favorites : LiveData<List<Movie>>
        get() = favoriteLiveData

    fun initMovieList() {
        movieDBInteractor.getMovies(object: MovieDBInteractor.GetMoviesCallback{
            override fun onSuccess(movies: List<Movie>) {
                moviesLiveData.postValue(movies)
            }

            override fun onError(error: String) {
                errorLiveData.postValue(error)
            }
        })
    }

    fun onMovieSelect(selectedMovie: Movie) {
        selectedMovieLiveData.postValue(selectedMovie)
    }

    fun addToFavorites(movie: Movie) {
        if (!favList.contains(movie))
            favList.add(movie)
        favoriteLiveData.postValue(favList)
    }

    fun onThemeChange() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    fun removeItemFromFavorites(movie: Movie) {
        favList.remove(movie)
        favoriteLiveData.postValue(null)
        favoriteLiveData.postValue(favList)
    }
}