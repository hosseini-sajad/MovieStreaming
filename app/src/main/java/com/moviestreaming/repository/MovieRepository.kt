package com.moviestreaming.repository

import com.moviestreaming.data.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(private val networkDataSource: NetworkDataSource) {
    suspend fun getGenres() = networkDataSource.getGenres().map {
        it.map { it.toEntity() }
    }.flowOn(IO)
}