package com.moviestreaming.utils

interface DomainMapper<T> {
    fun toEntity() : T
}