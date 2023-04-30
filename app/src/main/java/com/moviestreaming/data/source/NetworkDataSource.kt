package com.moviestreaming.data.source

import com.moviestreaming.data.source.network.dto.TopRateMovieResponse.TopRateMovie
import com.moviestreaming.data.source.network.dto.TrendingResponse.Trending

interface NetworkDataSource {
    suspend fun getTrending(): List<Trending>?

    suspend fun getTopRateMovie(): List<TopRateMovie>?
}