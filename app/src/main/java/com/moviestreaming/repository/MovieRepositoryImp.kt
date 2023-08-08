package com.moviestreaming.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.source.network.NetworkDataSource
import com.moviestreaming.utils.Result
import com.moviestreaming.utils.parsError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    override suspend fun getTopRateMovie(): Flow<Result<PagingData<TopRateMovieEntity>>> {
        return try {
            networkDataSource.getTopRateMovie().map { pagingData ->
                Result.Success(pagingData.map { it.toEntity() })
            }
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            flow { emit(Result.Error(errorResponse.statusMessage)) }
        }

    }/* = flow {
        try {
            emit(Result.Success(networkDataSource.getTopRateMovie()!!.map { it.toEntity() }))
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }*/

    override suspend fun getPopularMovies() = flow {
        try {
            emit(Result.Success(networkDataSource.getPopularMovies()!!.map { it.toEntity() }))
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

    override suspend fun getMovieDetails(movieId: Int) = flow {
        try {
            emit(Result.Success(networkDataSource.getMovieDetail(movieId)!!.toEntity()))
        }catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

    override suspend fun getMovieCredits(movieId: Int) = flow {
        try {
            emit(Result.Success(networkDataSource.getMovieCredits(movieId)!!.toEntity()))
        }catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

    override suspend fun getSimilarMovies(movieId: Int) = flow {
        try {
            emit(Result.Success(networkDataSource.getSimilarMovies(movieId)!!.similarMoviesDto.map { it.toEntity() }))
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

}