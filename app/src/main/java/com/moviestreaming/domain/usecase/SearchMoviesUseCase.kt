package com.moviestreaming.domain.usecase

import androidx.paging.PagingData
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String, filter: SearchFilter): Flow<PagingData<TopRateMovieEntity>> {
        return when (filter) {
            SearchFilter.BY_NAME -> repository.searchByName(query)
            SearchFilter.BY_DIRECTOR -> {
                emptyFlow()
//                repository.searchByDirector(query)
            }
            SearchFilter.BY_GENRE -> {
                emptyFlow()
//                repository.searchByGenre(query)
            }
            SearchFilter.BY_YEAR -> {
                emptyFlow()
//                repository.searchByYear(query)
            }
        }
    }
}

enum class SearchFilter {
    BY_NAME, BY_DIRECTOR, BY_GENRE, BY_YEAR;

    companion object {
        fun fromString(value: String): SearchFilter {
            return when (value) {
                "BY NAME" -> BY_NAME
                "BY DIRECTOR" -> BY_DIRECTOR
                "BY GENRE" -> BY_GENRE
                "BY YEAR" -> BY_YEAR
                else -> BY_NAME
            }
        }
    }
} 