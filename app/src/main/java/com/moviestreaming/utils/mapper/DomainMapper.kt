package com.moviestreaming.utils.mapper

interface DomainMapper<T> {
    fun toEntity() : T
}