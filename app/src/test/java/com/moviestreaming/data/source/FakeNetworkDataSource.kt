package com.moviestreaming.data.source

import com.moviestreaming.data.source.network.dto.TrendingResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal class FakeNetworkDataSource(private var trending: Flow<List<TrendingResponse.Trending>>? = emptyFlow()):
    NetworkDataSource {

    override suspend fun getTrending(): Flow<List<TrendingResponse.Trending>> {
        trending?.let {
            return  it
        }
        return emptyFlow()
    }

}