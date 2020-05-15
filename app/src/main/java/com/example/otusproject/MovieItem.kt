package com.example.otusproject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem(
    var title: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Int,
    val releaseDate: String
) : Parcelable