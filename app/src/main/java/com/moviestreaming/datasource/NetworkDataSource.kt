package com.moviestreaming.datasource

import com.moviestreaming.data.network.ApiService
import com.moviestreaming.data.network.INetworkDataSource
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val apiService: ApiService): INetworkDataSource {
    override suspend fun getGenres() = apiService.getAllGenre()
}