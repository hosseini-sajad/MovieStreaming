package com.moviestreaming.data.network

import com.moviestreaming.data.dto.GenreResponse.Genre
import retrofit2.http.GET

interface ApiService {
    @GET("genre/movie/list")
    suspend fun getAllGenre(): List<Genre>
}