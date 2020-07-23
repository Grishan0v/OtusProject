package com.example.otusproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.otusproject.data.App
import com.example.otusproject.ui.screen_home.HomeFragmentViewModel
import javax.inject.Inject

class ViewModelFactory:  ViewModelProvider.Factory{
    @Inject
    lateinit var homeFragmentViewModel: HomeFragmentViewModel

    init {
        App.instance.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == HomeFragmentViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            return homeFragmentViewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}