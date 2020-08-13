package com.example.otusproject.data

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    lateinit var api: MovieDbApi

    override fun onCreate() {
        super.onCreate()
        instance = this
        initRetrofit()
    }

    private  fun initRetrofit(){
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MovieDbApi::class.java)
    }

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        lateinit var instance: App
    }
}