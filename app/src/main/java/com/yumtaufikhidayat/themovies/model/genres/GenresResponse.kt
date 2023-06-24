package com.yumtaufikhidayat.themovies.model.genres


import com.google.gson.annotations.SerializedName
import com.yumtaufikhidayat.themovies.model.genres.Genre

data class GenresResponse(
    @SerializedName("genres")
    val genres: List<Genre> = listOf()
)