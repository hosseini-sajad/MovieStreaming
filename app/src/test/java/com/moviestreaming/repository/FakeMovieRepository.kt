package com.moviestreaming.repository

import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository() : MovieRepository {
    private var trending: MutableList<TrendingEntity> = mutableListOf()
    private var topRateMovies: MutableList<TopRateMovieEntity> = mutableListOf()
    override suspend fun getTrending(): Flow<Result<List<TrendingEntity>>> {
        return flow {
            if (trending.isNotEmpty()) {
                emit(Result.Success(trending))
                return@flow
            }
            emit(Result.Error("Server problem, please try later!"))
        }
    }

    override suspend fun getTopRateMovie(): Flow<Result<List<TopRateMovieEntity>>> {
        return flow {
            emit(Result.Error("Server problem, please try later!"))
        }
    }

    fun addTrending(lisOfTrending: List<TrendingEntity>) {
        trending.addAll(lisOfTrending)
    }
}