package com.example.otusproject.data.api

import com.example.otusproject.data.vo.JsonResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieDbService {

    @GET("movie/popular")
    fun getMoviesFromDB(): Call<JsonResponse>
}
