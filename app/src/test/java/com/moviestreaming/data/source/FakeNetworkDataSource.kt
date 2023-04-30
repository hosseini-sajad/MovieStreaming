package com.moviestreaming.data.source

import com.moviestreaming.data.source.network.dto.TopRateMovieResponse
import com.moviestreaming.data.source.network.dto.TrendingResponse

internal class FakeNetworkDataSource():
    NetworkDataSource {

    private val trendingMovies: MutableList<TrendingResponse.Trending>? = mutableListOf()
    private val topRateMovies: MutableList<TopRateMovieResponse.TopRateMovie>? = mutableListOf()

    override suspend fun getTrending(): List<TrendingResponse.Trending>? {
        trendingMovies?.let {
            return  it
        }
        return null
    }

    override suspend fun getTopRateMovie(): List<TopRateMovieResponse.TopRateMovie>? {
        topRateMovies?.let {
            return it
        }
        return null
    }

    fun addTrending(trending: List<TrendingResponse.Trending>) {
        trendingMovies?.addAll(trending)
    }

    fun addTopReteMovies(topRate: List<TopRateMovieResponse.TopRateMovie>) {
        topRateMovies?.addAll(topRate)
    }

}