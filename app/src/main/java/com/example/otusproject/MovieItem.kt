package com.example.otusproject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem(
    var title: String,
    val description: String,
    val moviePoster: String,
    val rating: String,
    val year: String,
    val director: String,
    val starring: String
) : Parcelable