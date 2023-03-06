package com.moviestreaming.repository

import com.moviestreaming.data.model.TrendingEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrending(): Flow<List<TrendingEntity>>
}