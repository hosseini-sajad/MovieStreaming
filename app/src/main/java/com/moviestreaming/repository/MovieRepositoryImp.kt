package com.moviestreaming.repository

import com.moviestreaming.data.source.NetworkDataSource
import com.moviestreaming.utils.UiState
import com.moviestreaming.utils.parsError
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val networkDataSource: NetworkDataSource) :
    MovieRepository {
    override suspend fun getTrending() = flow {
        try {
            if (networkDataSource.getTrending() == null)
                emit(UiState.Error("Server Error"))
            emit(UiState.Success(networkDataSource.getTrending()?.map { it.toEntity() }))
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(UiState.Error(errorResponse.statusMessage))
        }
    }.flowOn(IO)

}