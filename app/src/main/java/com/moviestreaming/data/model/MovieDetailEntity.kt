package com.moviestreaming.data.model

import com.moviestreaming.data.model.base.BaseEntity

data class MovieDetailEntity(
    override val id: Int,
    override val title: String?,
    override val image: String?,
    override val mediaType: String,
    val rate: Double,
    val releasedDate: String,
    val budget: Int,
    val description: String,
    val genres: List<GenreEntity>,
) : BaseEntity(id, title, image, mediaType)
