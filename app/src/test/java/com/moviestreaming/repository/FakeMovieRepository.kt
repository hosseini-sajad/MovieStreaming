package com.moviestreaming.repository

import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository() : MovieRepository {
    private lateinit var trending: MutableList<TrendingEntity>
    private lateinit var topRateMovies: MutableList<TopRateMovieEntity>
    private lateinit var popularMovies: MutableList<TopRateMovieEntity>
    override suspend fun getTrending(): Flow<Result<List<TrendingEntity>>> {
        return flow {
            if (::trending.isInitialized) {
                emit(Result.Success(trending))
                return@flow
            }
            emit(Result.Error("Server problem, please try later!"))
        }
    }

    override suspend fun getTopRateMovie(): Flow<Result<List<TopRateMovieEntity>>> {
        return flow {
            if (::topRateMovies.isInitialized) {
                emit(Result.Success(topRateMovies))
                return@flow
            }
            emit(Result.Error("Server problem, please try later!"))
        }
    }

    override suspend fun getPopularMovies(): Flow<Result<List<TopRateMovieEntity>>> {
        return flow {
            if (::popularMovies.isInitialized) {
                emit(Result.Success(popularMovies))
                return@flow
            }
            emit(Result.Error("Server problem, please try later!"))
        }
    }

    fun addTrending(lisOfTrending: List<TrendingEntity>) {
        trending = mutableListOf()
        trending.addAll(lisOfTrending)
    }

    fun addTopRateMovies(topRateList: List<TopRateMovieEntity>) {
        topRateMovies = mutableListOf()
        topRateMovies.addAll(topRateList)
    }

    fun addPopularMovies(popularList: List<TopRateMovieEntity>) {
        popularMovies = mutableListOf()
        popularMovies.addAll(popularList)
    }

}