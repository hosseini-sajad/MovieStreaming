package com.moviestreaming.data.source.network

import com.moviestreaming.data.source.NetworkDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkDataSourceImp @Inject constructor(private val apiService: ApiService):
    NetworkDataSource {
    override suspend fun getTrending() = flow {
        emit(apiService.getTrending().trending)
    }
}