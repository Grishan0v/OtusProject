package com.example.otusproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.otusproject.data.vo.MovieItem


@Database(entities = [MovieItem::class], version = 1)
abstract class AppDb : RoomDatabase(){
    abstract fun getMovieDao(): MovieDao
}