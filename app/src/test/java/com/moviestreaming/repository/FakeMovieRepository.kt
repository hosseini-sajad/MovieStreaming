package com.moviestreaming.repository

import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository() : MovieRepository {
    private lateinit var trending: MutableList<TrendingEntity>
    private lateinit var topRateMovies: MutableList<TopRateMovieEntity>
    private lateinit var popularMovies: MutableList<TopRateMovieEntity>
    private lateinit var movieDetail: MovieDetailEntity
    private lateinit var similarMovies: MutableList<TopRateMovieEntity>
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

    override suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieDetailEntity>> {
        return flow {
            if (::movieDetail.isInitialized) {
                emit(Result.Success(movieDetail))
                return@flow
            }
            emit(Result.Error("Server problem, please try later!"))
        }
    }

    override suspend fun getSimilarMovies(movieId: Int): Flow<Result<List<TopRateMovieEntity>>?> {
        return flow {
            if (::similarMovies.isInitialized) {
                emit(Result.Success(similarMovies))
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

    fun addMovieDetail(movieDetailEntity: MovieDetailEntity) {
        movieDetail = movieDetailEntity
    }

    fun addSimilarMovies(similarMoviesList: List<TopRateMovieEntity>) {
        similarMovies = mutableListOf()
        similarMovies.addAll(similarMoviesList)
    }

}