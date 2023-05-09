package com.moviestreaming.data.model

import com.moviestreaming.data.model.base.BaseEntity

data class TopRateMovieEntity(
    override val id: Int,
    override val title: String?,
    val image: String,
    val genre: Int,
    val media_type: String = "movie",
    val rate: Double
): BaseEntity(id, title)