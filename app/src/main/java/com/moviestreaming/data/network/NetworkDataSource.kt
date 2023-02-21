package com.moviestreaming.data.network

import com.moviestreaming.data.dto.GenreResponse.Genre
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {
    suspend fun getGenres(): Flow<List<Genre>>
}