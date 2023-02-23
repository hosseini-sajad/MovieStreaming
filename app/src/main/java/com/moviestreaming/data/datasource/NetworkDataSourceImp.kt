package com.moviestreaming.data.datasource

import com.moviestreaming.data.network.ApiService
import com.moviestreaming.data.network.NetworkDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkDataSourceImp @Inject constructor(private val apiService: ApiService):
    NetworkDataSource {
    override suspend fun getTrending() = flow {
        emit(apiService.getTrending().trending)
    }
}