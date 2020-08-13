package com.example.otusproject.data

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.otusproject.di.AppComponent
import com.example.otusproject.di.AppModule
import com.example.otusproject.di.DaggerAppComponent
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.disposables.CompositeDisposable

class App : Application() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDagger()

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        FirebaseCrashlytics.getInstance().setUserId("dev-01")
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("mTAG", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                Log.d("mTAG", token)
                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            })
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }


    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "6e63c2317fbe963d76c3bdc2b785f6d1"
        lateinit var instance: App
        lateinit var compositeDisposable: CompositeDisposable
    }
}