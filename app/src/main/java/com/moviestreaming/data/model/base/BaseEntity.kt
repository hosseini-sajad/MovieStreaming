package com.moviestreaming.data.model.base

abstract class BaseEntity(
    open val id: Int,
    open val title: String?,
    open val image: String?,
    open val mediaType: String
)