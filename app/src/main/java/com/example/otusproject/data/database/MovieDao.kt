package com.example.otusproject.data.database

import androidx.room.*
import com.example.otusproject.data.vo.MovieItem
import io.reactivex.Completable
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movies: List<MovieItem>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: MovieItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMovie(movie: MovieItem)

    @Query("DELETE FROM movie_table WHERE id = :itemId")
    fun delete(itemId: Int)

    @Query("SELECT * FROM movie_table")
    fun getAll(): List<MovieItem>

    @Query("DELETE FROM movie_table")
    fun nukeAll()

    @Query("SELECT * FROM movie_table WHERE isFavorite = 1")
    fun getAllFavorites() : Flowable<List<MovieItem>>

    @Query("SELECT * FROM movie_table WHERE id = :itemId")
    fun getById(itemId: Int): MovieItem


}