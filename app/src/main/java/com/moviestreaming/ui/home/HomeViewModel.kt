package com.moviestreaming.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}
