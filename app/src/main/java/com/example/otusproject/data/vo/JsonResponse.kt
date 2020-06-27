package com.example.otusproject.data.vo


import com.google.gson.annotations.SerializedName

data class JsonResponse(
    val page: Int,
    @SerializedName("results")
    val jsonMovies: List<JsonMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)