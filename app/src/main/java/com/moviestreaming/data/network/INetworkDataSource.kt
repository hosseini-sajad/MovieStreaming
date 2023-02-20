package com.moviestreaming.data.network

import com.moviestreaming.data.dto.GenreResponse.Genre

interface INetworkDataSource {
    suspend fun getGenres(): List<Genre>
}