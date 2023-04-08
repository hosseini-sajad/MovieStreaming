package com.moviestreaming.repository

import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.utils.UiState
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrending(): Flow<UiState<List<TrendingEntity>?>>
}