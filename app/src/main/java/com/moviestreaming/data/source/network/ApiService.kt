package com.moviestreaming.data.source.network

import com.moviestreaming.data.source.network.dto.CreditsDto
import com.moviestreaming.data.source.network.dto.MovieDetailDto
import com.moviestreaming.data.source.network.dto.SimilarMoviesDto
import com.moviestreaming.data.source.network.dto.TopRateMovieDto
import com.moviestreaming.data.source.network.dto.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("trending/all/week")
    suspend fun getTrending(): TrendingResponse

    @GET("movie/top_rated")
    suspend fun getTopRateMovie(
        @Query("page") page: Int
    ): TopRateMovieDto

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): TopRateMovieDto

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailDto

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(@Path("movieId") movieId: Int): CreditsDto

    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(@Path("movieId") movieId: Int): SimilarMoviesDto

    @GET("search/movie")
    suspend fun searchMovieByName(
        @Query("query") query: String,
        @Query("page") page: Int
    ): TopRateMovieDto
}