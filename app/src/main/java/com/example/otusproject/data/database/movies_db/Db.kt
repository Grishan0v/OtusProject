package com.example.otusproject.data.database.movies_db

import android.content.Context
import androidx.room.Room

object Db {

    private var INSTANCE: AppDb? = null

     fun getInstance(context: Context): AppDb? {

         if (INSTANCE == null) {
             synchronized(AppDb::class) {
                 INSTANCE = Room.databaseBuilder(
                     context,
                     AppDb::class.java,
                     "db-movie.db"
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
