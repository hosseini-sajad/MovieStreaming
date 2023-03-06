package com.moviestreaming.data.source

import com.moviestreaming.data.source.network.dto.TrendingResponse.Trending
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {
    suspend fun getTrending(): Flow<List<Trending>>
}