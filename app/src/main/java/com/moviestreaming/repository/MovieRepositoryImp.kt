package com.moviestreaming.repository

import android.util.Printer
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.data.source.network.NetworkDataSource
import com.moviestreaming.data.source.network.PopularMoviesRemoteDataSource
import com.moviestreaming.data.source.network.TopRatedMoviesRemoteDataSource
import com.moviestreaming.utils.parsError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val topRatedMoviesRemoteDataSource: TopRatedMoviesRemoteDataSource,
    private val popularMoviesRemoteDataSource: PopularMoviesRemoteDataSource
) : MovieRepository {
    override fun getTrending(): Flow<List<TrendingEntity>> = flow {
        val response = networkDataSource.getTrending()
        if (!response.isNullOrEmpty()) {
            emit(response.map { it.toEntity() })
        } else {
            throw Exception("No trending movies found.")
        }
    }.flowOn(Dispatchers.IO)

    override fun getTopRateMovie(): Flow<PagingData<TopRateMovieEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { topRatedMoviesRemoteDataSource }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getPopularMovies(): Flow<PagingData<TopRateMovieEntity>>  {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { popularMoviesRemoteDataSource }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getMovieDetails(movieId: Int) = flow {
        val movieDetail = networkDataSource.getMovieDetail(movieId)
        if (movieDetail != null) {
            emit(movieDetail.toEntity())
        } else {
            throw Exception("No movie details found.")
        }
    }.catch { e ->
        val errorResponse = parsError(e)
        throw Exception(errorResponse.statusMessage)
    }.flowOn(Dispatchers.IO)

    override fun getMovieCredits(movieId: Int) = flow {
        val movieCredits = networkDataSource.getMovieCredits(movieId)
        if (movieCredits != null) {
            emit(movieCredits.toEntity())
        } else {
            throw Exception("No movie credits found.")
        }
    }.catch { e ->
        val errorResponse = parsError(e)
        throw Exception(errorResponse.statusMessage)
    }.flowOn(Dispatchers.IO)

    override fun getSimilarMovies(movieId: Int): Flow<List<TopRateMovieEntity>?> = flow {
        val similarMovies = networkDataSource.getSimilarMovies(movieId)
        if (!similarMovies?.similarMoviesDto.isNullOrEmpty()) {
            emit(similarMovies?.similarMoviesDto?.map { it.toEntity() })
        } else {
            throw Exception("No similar movies found.")
        }
    }.catch { e ->
        val errorResponse = parsError(e)
        throw Exception(errorResponse.statusMessage)
    }.flowOn(Dispatchers.IO)

}