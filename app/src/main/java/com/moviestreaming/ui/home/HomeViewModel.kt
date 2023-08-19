package com.moviestreaming.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.repository.MovieRepository
import com.moviestreaming.utils.Result
import com.moviestreaming.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val mutableStateTrending = MutableStateFlow<UiState<List<TrendingEntity>>>(UiState.Loading())
    val trending: StateFlow<UiState<List<TrendingEntity>>> = mutableStateTrending

    private val mutableStateTopRateMovie = MutableStateFlow<UiState<PagingData<TopRateMovieEntity>>>(UiState.Loading())
    val topRateMovie: StateFlow<UiState<PagingData<TopRateMovieEntity>>> = mutableStateTopRateMovie

    private val mutableStatePopularMovies = MutableStateFlow<UiState<List<TopRateMovieEntity>>>(UiState.Loading())
    val popularMovies: StateFlow<UiState<List<TopRateMovieEntity>>> = mutableStatePopularMovies

    fun getTrending() {
        viewModelScope.launch {
            movieRepository.getTrending().collect {
                when (it) {
                    is Result.Success -> mutableStateTrending.value = UiState.Success(it.data)
                    is Result.Error -> mutableStateTrending.value = UiState.Error(it.message)
                }

            }
        }
    }

    fun getTopRateMovie() {
        viewModelScope.launch {
            movieRepository.getTopRateMovie().collect {
                when(it) {
                    is Result.Success -> {
                        mutableStateTopRateMovie.value = UiState.Success(it.data)
                    }
                    is Result.Error -> mutableStateTopRateMovie.value = UiState.Error(it.message)
                }
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            movieRepository.getPopularMovies().collect {
                when(it) {
                    is Result.Success -> mutableStatePopularMovies.value = UiState.Success(it.data)
                    is Result.Error -> mutableStatePopularMovies.value = UiState.Error(it.message)
                }
            }
        }
    }
}
