package com.moviestreaming.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviestreaming.data.model.GenreEntity
import com.moviestreaming.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val users = MutableStateFlow(HomeUiState.Success(emptyList()))
    val user: StateFlow<HomeUiState> get() = users

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getGenres() {
        viewModelScope.launch {
            movieRepository.getGenres().collect {
                users.value = HomeUiState.Success(it)
            }
        }
    }
}


// Represents different states for the LatestNews screen
sealed class HomeUiState {
    data class Success(val news: List<GenreEntity>): HomeUiState()
    data class Error(val exception: Throwable): HomeUiState()
}
