package com.moviestreaming.data.source.network

import com.moviestreaming.data.source.network.dto.CreditsDto
import com.moviestreaming.data.source.network.dto.MovieDetailDto
import com.moviestreaming.data.source.network.dto.SimilarMoviesDto
import com.moviestreaming.data.source.network.dto.TopRateMovieResponse.TopRateMovie
import com.moviestreaming.data.source.network.dto.TrendingResponse.Trending

interface NetworkDataSource {
    suspend fun getTrending(): List<Trending>?

    suspend fun getMovieDetail(movieId: Int): MovieDetailDto?

    suspend fun getMovieCredits(movieId: Int): CreditsDto?

    suspend fun getSimilarMovies(movieId: Int): SimilarMoviesDto?
}