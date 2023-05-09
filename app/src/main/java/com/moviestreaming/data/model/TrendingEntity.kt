package com.moviestreaming.data.model

import com.moviestreaming.data.model.base.BaseEntity

data class TrendingEntity(
    override val id: Int,
    override val title: String?,
    override val image: String,
    override val mediaType: String
) : BaseEntity(id, title, image, mediaType)