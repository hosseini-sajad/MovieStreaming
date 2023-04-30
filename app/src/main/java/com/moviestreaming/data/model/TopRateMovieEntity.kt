package com.moviestreaming.data.model

data class TopRateMovieEntity(
    val id: Int,
    val title: String?,
    val image: String,
    val genre: Int,
    val media_type: String = "movie",
    val rate: Double
)