package com.example.otusproject.data.database

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
                     "db-movie1.db"
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
