package com.moviestreaming.data.network

import com.moviestreaming.data.dto.GenreResponse.Genre

interface NetworkDataSource {
    suspend fun getGenres(): List<Genre>
}