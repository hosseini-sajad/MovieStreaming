package com.moviestreaming.repository

import com.moviestreaming.data.source.NetworkDataSource
import com.moviestreaming.utils.Result
import com.moviestreaming.utils.parsError
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val networkDataSource: NetworkDataSource) :
    MovieRepository {
    override suspend fun getTrending() = flow {
        try {
            emit(Result.Success(networkDataSource.getTrending()!!.map { it.toEntity() }))
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

}