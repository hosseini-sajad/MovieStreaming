package com.moviestreaming.data.network

import com.moviestreaming.data.dto.GenreResponse
import retrofit2.http.GET

interface ApiService {
    @GET("genre/movie/list")
    suspend fun getAllGenre(): GenreResponse
}