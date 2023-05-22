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

    override suspend fun getTopRateMovie() = flow {
        try {
            emit(Result.Success(networkDataSource.getTopRateMovie()!!.map { it.toEntity() }))
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

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

            val movieDetail = networkDataSource.getMovieDetail(movieId)

            emit(movieDetail?.let { Result.Success(it.toEntity()) })
        }catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

    override suspend fun getSimilarMovies(movieId: Int) = flow {
        try {
            emit(networkDataSource.getSimilarMovies(movieId)?.similarMoviesDto?.let { similarMoviesDto ->
                Result.Success(
                    similarMoviesDto.map { it.toEntity() })
            })
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

}