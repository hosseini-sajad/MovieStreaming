package com.moviestreaming.datasource

import com.moviestreaming.data.network.ApiService
import com.moviestreaming.data.network.NetworkDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieNetworkDataSource @Inject constructor(private val apiService: ApiService):
    NetworkDataSource {
    override suspend fun getGenres() = flow {
        val genres = apiService.getAllGenre().genres.map {
            it.toEntity()
        }
        emit(genres)
    }
}