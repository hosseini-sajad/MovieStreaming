package com.moviestreaming.data.datasource

import com.moviestreaming.data.dto.TrendingResponse
import com.moviestreaming.data.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal class FakeNetworkDataSource(private var trending: Flow<List<TrendingResponse.Trending>>? = emptyFlow()): NetworkDataSource {

    override suspend fun getTrending(): Flow<List<TrendingResponse.Trending>> {
        trending?.let {
            return  it
        }
        return emptyFlow()
    }

}