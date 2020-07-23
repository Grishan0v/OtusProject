package com.example.otusproject.di

import com.example.otusproject.data.App
import com.example.otusproject.data.repository.MovieDbUseCase
import com.example.otusproject.ui.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    DataBaseModule::class,
    NetworkModule::class
])
interface AppComponent {
    fun inject(app: App)
    fun inject(useCase: MovieDbUseCase)
    fun inject(viewModelFactory: ViewModelFactory)
}