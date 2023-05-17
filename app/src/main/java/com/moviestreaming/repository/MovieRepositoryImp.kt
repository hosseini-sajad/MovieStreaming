package com.moviestreaming.repository

import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.data.source.NetworkDataSource
import com.moviestreaming.utils.Constants
import com.moviestreaming.utils.Result
import com.moviestreaming.utils.getImageUrl
import com.moviestreaming.utils.parsError
import com.moviestreaming.utils.roundedTo2Decimal
import com.moviestreaming.utils.showYear
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val networkDataSource: NetworkDataSource) :
    MovieRepository {
    override suspend fun getTrending() = flow {
        try {
            emit(Result.Success(networkDataSource.getTrending()!!.map { it.toEntity() }))
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

    override suspend fun getTopRateMovie() = flow {
        try {
            emit(Result.Success(networkDataSource.getTopRateMovie()!!.map { it.toEntity() }))
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

    override suspend fun getPopularMovies() = flow {
        try {
            emit(Result.Success(networkDataSource.getPopularMovies()!!.map { it.toEntity() }))
        } catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

    override suspend fun getMovieDetails(movieId: Int) = flow {
        try {

            val movieDetail = networkDataSource.getMovieDetail(movieId)

            val movieDetailEntity = MovieDetailEntity(
                movieDetail.id,
                movieDetail.title,
                getImageUrl(movieDetail.backdropPath),
                Constants.MOVIE,
                roundedTo2Decimal(movieDetail.voteAverage),
                showYear(movieDetail.releaseDate),
                movieDetail.budget,
                movieDetail.overview,
                movieDetail.genresDto.map { it.toEntity() }
            )
            emit(Result.Success(movieDetailEntity))
        }catch (e: Exception) {
            val errorResponse = parsError(e)
            emit(Result.Error(errorResponse.statusMessage))
        }
    }

}