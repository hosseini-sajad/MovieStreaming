package com.moviestreaming.data.dto


import androidx.annotation.Keep

@Keep
data class GenreResponse(
    val genres: List<Genre>
) {
    @Keep
    data class Genre(
        val id: Int,
        val name: String
    )
}