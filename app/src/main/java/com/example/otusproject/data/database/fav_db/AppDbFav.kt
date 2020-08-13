package com.example.otusproject.data.database.fav_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.otusproject.data.database.movies_db.MovieDao
import com.example.otusproject.data.vo.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDbFav: RoomDatabase() {
    abstract fun getMovieDao(): MovieFavDAO
}