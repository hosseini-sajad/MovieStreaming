package com.moviestreaming.repository

import androidx.paging.PagingData
import com.moviestreaming.data.model.CreditsEntity
import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.utils.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrending(): Flow<Result<List<TrendingEntity>>>

    suspend fun getTopRateMovie(): Flow<Result<PagingData<TopRateMovieEntity>>>

    suspend fun getPopularMovies(): Flow<Result<List<TopRateMovieEntity>>>

    suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieDetailEntity>?>

    suspend fun getMovieCredits(movieId: Int): Flow<Result<CreditsEntity>?>

    suspend fun getSimilarMovies(movieId: Int): Flow<Result<List<TopRateMovieEntity>>?>
}