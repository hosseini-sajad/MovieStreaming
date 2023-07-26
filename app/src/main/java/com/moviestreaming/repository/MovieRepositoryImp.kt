package com.moviestreaming.repository

import androidx.paging.map
import com.moviestreaming.data.source.network.NetworkDataSource
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

    override suspend fun getTopRateMovie() = flow {
        try {
            networkDataSource.getTopRateMovie().collect { pagingData ->
                emit(Result.Success(pagingData.map { it.toEntity() }))
            }
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
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