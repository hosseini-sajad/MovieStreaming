package com.moviestreaming.repository

import com.moviestreaming.data.source.NetworkDataSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val networkDataSource: NetworkDataSource) :
    MovieRepository {
    override suspend fun getTrending() = flow {
        try {
            emit(networkDataSource.getTrending().map { it.toEntity() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }.flowOn(IO)

}