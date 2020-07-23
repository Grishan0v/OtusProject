package com.example.otusproject.di

import android.app.Application
import com.example.otusproject.data.api.MovieDbService
import com.example.otusproject.data.repository.MovieDbUseCase
import com.example.otusproject.data.repository.MoviesRepository
import com.example.otusproject.ui.screen_home.HomeFragmentViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideUseCase(application: Application, service: MovieDbService, repository: MoviesRepository): MovieDbUseCase {
        return MovieDbUseCase(application, service, repository)
    }

    @Provides
    @Singleton
    fun provideHomeFragmentViewModel(movieDbUseCase: MovieDbUseCase): HomeFragmentViewModel {
        return HomeFragmentViewModel(movieDbUseCase)
    }
}