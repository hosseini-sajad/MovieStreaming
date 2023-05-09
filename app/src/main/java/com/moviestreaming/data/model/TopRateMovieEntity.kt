package com.moviestreaming.data.model

import com.moviestreaming.data.model.base.BaseEntity

data class TopRateMovieEntity(
    override val id: Int,
    override val title: String?,
    override val image: String,
    override val mediaType: String = "movie",
    val genre: Int,
    val rate: Double
): BaseEntity(id, title, image, mediaType)