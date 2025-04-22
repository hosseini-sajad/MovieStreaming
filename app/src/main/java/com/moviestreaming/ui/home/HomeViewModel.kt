package com.moviestreaming.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.repository.MovieRepository
import com.moviestreaming.utils.parsError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
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
                val topIMDb = async {
                    movieRepository.getTopRateMovie()
                        .catch { exception ->
                            val errorResponse = parsError(exception)
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = errorResponse.statusMessage
                            )
                        }.first()
                }
                val popular = async {
                    movieRepository.getPopularMovies()
                        .catch { exception ->
                            val errorResponse = parsError(exception)
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = errorResponse.statusMessage
                            )
                        }.first()
                }

                _uiState.value = HomeUiState(
                    trendingMovies = trending.await(),
                    topIMDbMovies = topIMDb.await(),
                    popularMovies = popular.await(),
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

//    fun getTrending() {
//        viewModelScope.launch {
//            movieRepository.getTrending().collect {
//                when (it) {
//                    is Result.Success -> mutableStateTrending.value = UiState.Success(it.data)
//                    is Result.Error -> mutableStateTrending.value = UiState.Error(it.message)
//                }
//
//            }
//        }
//    }
//
//    fun getTopRateMovie() {
//        viewModelScope.launch {
//            movieRepository.getTopRateMovie().collect {
//                when(it) {
//                    is Result.Success -> {
//                        mutableStateTopRateMovie.value = UiState.Success(it.data)
//                    }
//                    is Result.Error -> mutableStateTopRateMovie.value = UiState.Error(it.message)
//                }
//            }
//        }
//    }
//
//    fun getPopularMovies() {
//        viewModelScope.launch {
//            movieRepository.getPopularMovies().collect {
//                when(it) {
//                    is Result.Success -> mutableStatePopularMovies.value = UiState.Success(it.data)
//                    is Result.Error -> mutableStatePopularMovies.value = UiState.Error(it.message)
//                }
//            }
//        }
//    }
}

data class HomeUiState(
    val trendingMovies: List<TrendingEntity> = emptyList(),
    val topIMDbMovies: List<TopRateMovieEntity> = emptyList(),
    val popularMovies: List<TopRateMovieEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
