package com.moviestreaming.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.repository.MovieRepository
import com.moviestreaming.utils.Result
import com.moviestreaming.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val mutableStateMovieDetail = MutableStateFlow<UiState<MovieDetailEntity>>(UiState.Loading())
    val movieDetail: StateFlow<UiState<MovieDetailEntity>> = mutableStateMovieDetail

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieId).collect {
                when (it) {
                    is Result.Success -> mutableStateMovieDetail.value = UiState.Success(it.data)
                    is Result.Error -> mutableStateMovieDetail.value = UiState.Error(it.message)
                }
            }
        }
    }
}