package com.moviestreaming.data.source.network

import androidx.paging.PagingData
import com.moviestreaming.data.source.network.dto.CreditsDto
import com.moviestreaming.data.source.network.dto.MovieDetailDto
import com.moviestreaming.data.source.network.dto.SimilarMoviesDto
import com.moviestreaming.data.source.network.dto.TopRateMovieResponse.TopRateMovie
import com.moviestreaming.data.source.network.dto.TrendingResponse.Trending
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {
    suspend fun getTrending(): List<Trending>?

    suspend fun getTopRateMovie(): Flow<PagingData<TopRateMovie>>

    suspend fun getPopularMovies(): List<TopRateMovie>?

    suspend fun getMovieDetail(movieId: Int): MovieDetailDto?

    suspend fun getMovieCredits(movieId: Int): CreditsDto?

    suspend fun getSimilarMovies(movieId: Int): SimilarMoviesDto?
}