package com.moviestreaming.data.network

import com.moviestreaming.data.dto.TrendingResponse.Trending
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {
    suspend fun getTrending(): Flow<List<Trending>>
}