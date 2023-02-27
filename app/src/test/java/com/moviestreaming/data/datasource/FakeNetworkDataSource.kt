package com.moviestreaming.data.datasource

import com.moviestreaming.data.dto.TrendingResponse
import com.moviestreaming.data.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class FakeNetworkDataSource(var trending: Flow<TrendingResponse.Trending>? = emptyFlow()): NetworkDataSource {

    override suspend fun getTrending(): Flow<List<TrendingResponse.Trending>> {
        trending?.let {
            return flow { it.map { it.toEntity() } }
        }
        return emptyFlow()
    }

}