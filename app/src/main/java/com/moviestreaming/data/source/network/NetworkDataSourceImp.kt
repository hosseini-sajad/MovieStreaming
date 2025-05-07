package com.moviestreaming.data.source.network

import javax.inject.Inject

class NetworkDataSourceImp @Inject constructor(
    private val apiService: ApiService
) : NetworkDataSource {
    override suspend fun getTrending() = apiService.getTrending().trending
    override suspend fun getMovieDetail(movieId: Int) = apiService.getMovieDetail(movieId)
    override suspend fun getMovieCredits(movieId: Int) = apiService.getMovieCredits(movieId)
    override suspend fun getSimilarMovies(movieId: Int) = apiService.getSimilarMovies(movieId)
}