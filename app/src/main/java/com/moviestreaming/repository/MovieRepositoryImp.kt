package com.moviestreaming.repository

import com.moviestreaming.data.model.TrailerEntity
import com.moviestreaming.data.source.network.NetworkDataSource
import com.moviestreaming.utils.Result
import com.moviestreaming.utils.parsError
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
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

            val apiResponse = networkDataSource.getSimilarMovies(movieId)
            val similarMoviesDto = apiResponse!!.similarMoviesDto
            val similarMoviesEntity = similarMoviesDto.map {
                it.toEntity()
            }
            emit(Result.Success(similarMoviesEntity))
        } catch (e: Exception) {
            e.printStackTrace()
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

    override suspend fun getMovieTrailer(movieId: Int): Result<List<TrailerEntity>>? {
        val apiResponse = networkDataSource.getMovieTrailer(movieId)
        if (apiResponse.isSuccessful) {
            val trailers = apiResponse.body()?.trailers
            if (!trailers.isNullOrEmpty()) {
                return Result.Success(trailers.map { it.toEntity() })
            }
            return Result.Success(listOf())
        } else {
            val errorMessage = apiResponse.errorBody().toString().let {
                JSONObject(it).getString("message")
            }
            return Result.Error(errorMessage)
        }
    }


}