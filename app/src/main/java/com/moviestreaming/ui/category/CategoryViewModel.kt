package com.moviestreaming.ui.category

import androidx.lifecycle.ViewModel
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.repository.MovieRepository
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {


}

data class CategoryUiState(
    val movies: List<TopRateMovieEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)