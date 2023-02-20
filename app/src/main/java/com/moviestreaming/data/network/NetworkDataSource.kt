package com.moviestreaming.data.network

import com.moviestreaming.data.model.GenreEntity
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {
    suspend fun getGenres(): Flow<List<GenreEntity>>
}