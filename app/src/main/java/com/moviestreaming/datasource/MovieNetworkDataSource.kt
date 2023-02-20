package com.moviestreaming.datasource

import com.moviestreaming.data.network.ApiService
import com.moviestreaming.data.network.NetworkDataSource
import javax.inject.Inject

class MovieNetworkDataSource @Inject constructor(private val apiService: ApiService):
    NetworkDataSource {
    override suspend fun getGenres() = apiService.getAllGenre().genres
}