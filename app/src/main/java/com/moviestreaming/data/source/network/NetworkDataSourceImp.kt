package com.moviestreaming.data.source.network

import com.moviestreaming.data.source.NetworkDataSource
import javax.inject.Inject

class NetworkDataSourceImp @Inject constructor(private val apiService: ApiService) :
    NetworkDataSource {
    override suspend fun getTrending() = apiService.getTrending().trending
}