package com.example.otusproject.data.api

import android.app.Application
import android.util.Log
import com.example.otusproject.data.database.AppDb
import com.example.otusproject.data.database.Db
import com.example.otusproject.data.repository.MovieDbUseCase
import com.example.otusproject.data.repository.MoviesRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class App : Application() {
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var service:  MovieDbService
    lateinit var useCase:  MovieDbUseCase

    private var roomDb: AppDb? = null


    override fun onCreate() {
        super.onCreate()
        instance = this
        initRoomDb()
        initRetrofit()
        moviesRepository = MoviesRepository(roomDb!!.getMovieDao())
        initUseCase()
    }

    private fun initUseCase() {
        useCase = MovieDbUseCase(applicationContext, service, moviesRepository)
    }

    private fun initRoomDb() {
        Executors.newSingleThreadScheduledExecutor().execute {
            roomDb = Db.getInstance(this)
            Log.d("MyTag", roomDb.toString())
        }
    }

    private  fun initRetrofit(){
        val requestInterceptor = Interceptor { chain ->

            //создаем запрос на добавление в url параметра api key
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(MovieDbService::class.java)
    }

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "6e63c2317fbe963d76c3bdc2b785f6d1"
        lateinit var instance: App
    }
}