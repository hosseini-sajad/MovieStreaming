package com.moviestreaming.data.source

import com.moviestreaming.data.source.network.dto.TrendingResponse

internal class FakeNetworkDataSource(private var trending: List<TrendingResponse.Trending>?):
    NetworkDataSource {

    override suspend fun getTrending(): List<TrendingResponse.Trending> {
        trending?.let {
            return  it
        }
        return emptyList()
    }

}