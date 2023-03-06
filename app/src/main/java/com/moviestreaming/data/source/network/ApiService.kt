package com.moviestreaming.data.source.network

import com.moviestreaming.data.source.network.dto.TrendingResponse
import retrofit2.http.GET

interface ApiService {
    @GET("trending/all/week")
    suspend fun getTrending(): TrendingResponse
}