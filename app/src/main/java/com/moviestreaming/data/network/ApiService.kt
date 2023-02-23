package com.moviestreaming.data.network

import com.moviestreaming.data.dto.TrendingResponse
import retrofit2.http.GET

interface ApiService {
    @GET("trending/all/week")
    suspend fun getTrending(): TrendingResponse
}