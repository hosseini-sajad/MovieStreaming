package com.moviestreaming.repository

import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.utils.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrending(): Flow<Result<List<TrendingEntity>>>

    suspend fun getTopRateMovie(): Flow<Result<List<TopRateMovieEntity>>>

    suspend fun getPopularMovies(): Flow<Result<List<TopRateMovieEntity>>>

    suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieDetailEntity>?>

    suspend fun getSimilarMovies(movieId: Int): Flow<Result<List<TopRateMovieEntity>>?>
}