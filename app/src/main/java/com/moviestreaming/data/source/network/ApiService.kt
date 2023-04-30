package com.moviestreaming.data.source.network

import com.moviestreaming.data.source.network.dto.TopRateMovieResponse
import com.moviestreaming.data.source.network.dto.TrendingResponse
import retrofit2.http.GET

interface ApiService {
    @GET("trending/all/week")
    suspend fun getTrending(): TrendingResponse

    @GET("movie/top_rated")
    suspend fun getTopRateMovie(): TopRateMovieResponse
}