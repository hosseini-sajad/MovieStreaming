package com.moviestreaming.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val topRatedUiState: StateFlow<CategoryUiState> = createMovieStateFlow(
        flow = movieRepository.getTopRateMovie()
    )

    val popularUiState: StateFlow<CategoryUiState> = createMovieStateFlow(
        flow = movieRepository.getPopularMovies()
    )

    private fun createMovieStateFlow(
        flow: Flow<List<TopRateMovieEntity>>
    ): StateFlow<CategoryUiState> = flow
        .map { movies -> CategoryUiState.Success(movies) }
        .onStart { CategoryUiState.Loading }
        .catch { e -> CategoryUiState.Error(e.message ?: "Failed to load movies") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000),
            initialValue = CategoryUiState.Loading
        )
}

sealed class CategoryUiState {
    object Loading : CategoryUiState()
    data class Success(val movies: List<TopRateMovieEntity>) : CategoryUiState()
    data class Error(val message: String) : CategoryUiState()
}