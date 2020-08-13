package com.example.otusproject.data.database.movies_db

import androidx.room.*
import com.example.otusproject.data.vo.Movie


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Update
    fun updateMovie(movies: List<Movie>)

    @Query("SELECT * FROM movie_table")
    fun getAll(): List<Movie>

}