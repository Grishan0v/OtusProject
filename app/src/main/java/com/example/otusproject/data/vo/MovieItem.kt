package com.example.otusproject.data.vo

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieItem (
    val adult: Boolean,
    val backdropPath: String,
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    var isFavorite: Boolean = false
)