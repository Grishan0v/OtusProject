package com.example.otusproject.data.database.fav_db

import android.content.Context
import androidx.room.Room

object DbFav {

    private var INSTANCE: AppDbFav? = null

    fun getInstance(context: Context): AppDbFav? {

        if (INSTANCE == null) {
            synchronized(AppDbFav::class) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDbFav::class.java,
                    "db-movie-fav.db"
                )
                    .build()
            }
        }
        return INSTANCE
    }

    fun destroyInstance() {
        INSTANCE?.close()
        INSTANCE = null
    }
}