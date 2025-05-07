package com.moviestreaming.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.moviestreaming.core.usecase.GetPopularMoviesUseCase
import com.moviestreaming.core.usecase.GetTopRatedMoviesUseCase
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
    private val movieRepository: MovieRepository,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    val topRatedUiState: StateFlow<CategoryUiState> = createMovieStateFlow(
        flow = getTopRatedMoviesUseCase.invoke()
    )

    val popularUiState: StateFlow<CategoryUiState> = createMovieStateFlow(
        flow = getPopularMoviesUseCase.invoke()
    )

    private fun createMovieStateFlow(
        flow: Flow<PagingData<TopRateMovieEntity>>
    ): StateFlow<CategoryUiState> = flow
        .map { CategoryUiState.Success(flow) }
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
    data class Success(val movies: Flow<PagingData<TopRateMovieEntity>>) : CategoryUiState()
    data class Error(val message: String) : CategoryUiState()
}