package com.yumtaufikhidayat.themovies.model.cast


import com.google.gson.annotations.SerializedName
import com.yumtaufikhidayat.themovies.model.cast.Cast
import com.yumtaufikhidayat.themovies.model.cast.Crew

data class MovieCastResponse(
    @SerializedName("cast")
    val cast: List<Cast> = listOf(),
    @SerializedName("crew")
    val crew: List<Crew> = listOf(),
    @SerializedName("id")
    val id: Int = 0 // 985939
)