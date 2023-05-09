package com.moviestreaming.data.model

import com.moviestreaming.data.model.base.BaseEntity

data class TrendingEntity(
    override val id: Int,
    override val title: String?,
    val image: String,
    val media_type: String
) : BaseEntity(id, title)