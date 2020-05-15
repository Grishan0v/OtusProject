package com.example.otusproject.data

import com.example.otusproject.MovieItemModel
import retrofit2.Call
import retrofit2.http.GET

interface MovieDbApi {

    @GET("movie/" + "481848?api_key=a99d1887856cb13c01fef273bdb33c83")
    fun getMoviesFromDB(): Call<List<MovieItemModel>>
}
