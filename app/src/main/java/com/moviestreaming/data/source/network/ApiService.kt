package com.moviestreaming.data.source.network

import com.moviestreaming.data.source.network.dto.CreditsDto
import com.moviestreaming.data.source.network.dto.MovieDetailDto
import com.moviestreaming.data.source.network.dto.SimilarMoviesDto
import com.moviestreaming.data.source.network.dto.TopRateMovieResponse
import com.moviestreaming.data.source.network.dto.TrailerResponseDto
import com.moviestreaming.data.source.network.dto.TrendingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("trending/all/week")
    suspend fun getTrending(): TrendingResponse

    @GET("movie/top_rated")
    suspend fun getTopRateMovie(): TopRateMovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(): TopRateMovieResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailDto

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(@Path("movieId") movieId: Int): CreditsDto

    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(@Path("movieId") movieId: Int): SimilarMoviesDto

    @GET("movie/{movieId}/videos")
    suspend fun getMovieTrailer(@Path("movieId") movieId: Int): Response<TrailerResponseDto>
}