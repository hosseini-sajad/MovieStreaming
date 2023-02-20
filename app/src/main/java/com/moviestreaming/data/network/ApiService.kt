package com.moviestreaming.data.network

import retrofit2.http.GET

interface ApiService {
    @GET("genre/movie/list")
    suspend fun getAllGenre()
}