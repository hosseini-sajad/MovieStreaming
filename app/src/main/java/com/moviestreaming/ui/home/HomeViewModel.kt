package com.moviestreaming.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moviestreaming.core.usecase.GetPopularMoviesUseCase
import com.moviestreaming.core.usecase.GetTopRatedMoviesUseCase
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.repository.MovieRepository
import com.moviestreaming.utils.parsError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchMovies()
    }

    fun retry() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                val trending = async {
                    movieRepository.getTrending()
                        .catch { exception ->
                            val errorResponse = parsError(exception)
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = errorResponse.statusMessage
                            )
                        }
                        .first()
                }
                _uiState.value = HomeUiState(
                    trendingMovies = trending.await(),
                    topIMDbMovies = getTopRatedMoviesUseCase.invoke().cachedIn(viewModelScope),
                    popularMovies = getPopularMoviesUseCase.invoke().cachedIn(viewModelScope),
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Failed to fetch movies"
                )
            }
        }
    }
}

data class HomeUiState(
    val trendingMovies: List<TrendingEntity> = emptyList(),
    val topIMDbMovies: Flow<PagingData<TopRateMovieEntity>> = emptyFlow(),
    val popularMovies: Flow<PagingData<TopRateMovieEntity>> = emptyFlow(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
