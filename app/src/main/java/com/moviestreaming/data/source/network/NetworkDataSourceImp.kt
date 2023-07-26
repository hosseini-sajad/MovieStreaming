package com.moviestreaming.data.source.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moviestreaming.data.source.network.dto.TopRateMovieResponse
import com.moviestreaming.data.source.paging.MoviesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkDataSourceImp @Inject constructor(private val apiService: ApiService) :
    NetworkDataSource {
    override suspend fun getTrending() = apiService.getTrending().trending
    override suspend fun getTopRateMovie(): Flow<PagingData<TopRateMovieResponse.TopRateMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = apiService.getTopRateMovie(1).totalPages,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(apiService)
            }
        ).flow
    }
    override suspend fun getPopularMovies() = apiService.getPopularMovies().topRateMovies
    override suspend fun getMovieDetail(movieId: Int) = apiService.getMovieDetail(movieId)
    override suspend fun getMovieCredits(movieId: Int) = apiService.getMovieCredits(movieId)
    override suspend fun getSimilarMovies(movieId: Int) = apiService.getSimilarMovies(movieId)
}