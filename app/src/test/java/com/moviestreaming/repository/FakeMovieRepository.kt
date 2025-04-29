package com.moviestreaming.repository

import com.moviestreaming.data.model.CreditsEntity
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
    private lateinit var creditsEntity: CreditsEntity
    override suspend fun getTrending(): Flow<List<TrendingEntity>> {
        return flow {
            if (::trending.isInitialized) {
                emit(trending)
                return@flow
            }
            throw Exception()
        }
    }

    override suspend fun getTopRateMovie(): Flow<List<TopRateMovieEntity>> {
        return flow {
            if (::topRateMovies.isInitialized) {
                emit(topRateMovies)
                return@flow
            }
            throw Exception()
        }
    }

    override suspend fun getPopularMovies(): Flow<List<TopRateMovieEntity>> {
        return flow {
            if (::popularMovies.isInitialized) {
                emit(popularMovies)
                return@flow
            }
            throw Exception()
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

    override suspend fun getMovieCredits(movieId: Int): Flow<Result<CreditsEntity>?> {
        return flow {
            if (::creditsEntity.isInitialized) {
                emit(Result.Success(creditsEntity))
                return@flow
            }
            emit(Result.Error("Server problem, please try later!"))
        }
    }

    override suspend fun getSimilarMovies(movieId: Int): Flow<List<TopRateMovieEntity>?> {
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

    fun addMovieCredits(movieCreditsEntity: CreditsEntity) {
        creditsEntity = movieCreditsEntity
    }

}