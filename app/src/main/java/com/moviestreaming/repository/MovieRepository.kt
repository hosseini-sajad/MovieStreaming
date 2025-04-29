package com.moviestreaming.repository

import com.moviestreaming.data.model.CreditsEntity
import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTrending(): Flow<List<TrendingEntity>>

    fun getTopRateMovie(): Flow<List<TopRateMovieEntity>>

    fun getPopularMovies(): Flow<List<TopRateMovieEntity>>

    fun getMovieDetails(movieId: Int): Flow<MovieDetailEntity>

    fun getMovieCredits(movieId: Int): Flow<CreditsEntity>

    fun getSimilarMovies(movieId: Int): Flow<List<TopRateMovieEntity>?>
}