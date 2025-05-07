package com.moviestreaming.repository

import androidx.paging.PagingData
import com.moviestreaming.data.model.CreditsEntity
import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTrending(): Flow<List<TrendingEntity>>

    fun getTopRateMovie(): Flow<PagingData<TopRateMovieEntity>>

    fun getPopularMovies(): Flow<PagingData<TopRateMovieEntity>>

    fun getMovieDetails(movieId: Int): Flow<MovieDetailEntity>

    fun getMovieCredits(movieId: Int): Flow<CreditsEntity>

    fun getSimilarMovies(movieId: Int): Flow<List<TopRateMovieEntity>?>
}