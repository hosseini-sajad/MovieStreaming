package com.moviestreaming.domain.usecase

import androidx.paging.PagingData
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<TopRateMovieEntity>> {
        return movieRepository.getTopRateMovie()
    }
}
