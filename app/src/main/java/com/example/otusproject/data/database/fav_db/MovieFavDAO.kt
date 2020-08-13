package com.example.otusproject.data.database.fav_db

import androidx.room.*
import com.example.otusproject.data.vo.Movie

@Dao
interface MovieFavDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(movie: Movie)

    @Delete
    fun deleteFromFavorite(movie: Movie)

    @Query("SELECT * FROM movie_table")
    fun getAllFavorites(): List<Movie>
}