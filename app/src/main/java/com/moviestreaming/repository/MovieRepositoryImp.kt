package com.moviestreaming.repository

import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.data.source.network.NetworkDataSource
import com.moviestreaming.utils.Result
import com.moviestreaming.utils.parsError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : MovieRepository {
    override suspend fun getTrending(): Flow<List<TrendingEntity>> = flow {
        val response = networkDataSource.getTrending()
        if (!response.isNullOrEmpty()) {
            emit(response.map { it.toEntity() })
        } else {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTopRateMovie(): Flow<List<TopRateMovieEntity>> = flow {
        val response = networkDataSource.getTopRateMovie()
        if (!response.isNullOrEmpty()) {
            emit(response.map { it.toEntity() })
        } else {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPopularMovies(): Flow<List<TopRateMovieEntity>> = flow {
        val response = networkDataSource.getPopularMovies()
        if (!response.isNullOrEmpty()) {
            emit(response.map { it.toEntity() })
        } else {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieDetails(movieId: Int) = flow {
        try {
            emit(Result.Success(networkDataSource.getMovieDetail(movieId)!!.toEntity()))
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

    override suspend fun getMovieCredits(movieId: Int) = flow {
        try {
            emit(Result.Success(networkDataSource.getMovieCredits(movieId)!!.toEntity()))
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

    override suspend fun getSimilarMovies(movieId: Int) = flow {
        try {

            val apiResponse = networkDataSource.getSimilarMovies(movieId)
            val similarData = apiResponse!!.similarMoviesDto
            val mappedData = similarData.map {
                it.toEntity()
            }
            emit(Result.Success(mappedData))
        } catch (e: Exception) {
            e.printStackTrace()
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }


}