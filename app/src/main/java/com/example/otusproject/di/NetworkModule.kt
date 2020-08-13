package com.example.otusproject.di

import com.example.otusproject.data.App
import com.example.otusproject.data.api.MovieDbService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideInterceptor(): Interceptor =
        Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", App.API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }

    @Provides
    fun provideOkHttpClient (interceptor: Interceptor): OkHttpClient =
         OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(App.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideNetworkService(retrofit: Retrofit): MovieDbService =
        retrofit.create(MovieDbService::class.java)
}
