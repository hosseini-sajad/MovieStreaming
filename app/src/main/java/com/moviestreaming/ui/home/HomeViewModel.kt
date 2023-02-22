package com.moviestreaming.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviestreaming.data.model.GenreEntity
import com.moviestreaming.repository.MovieRepository
import com.moviestreaming.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val mutableStateGenres = MutableStateFlow<UiState<List<GenreEntity>>>(UiState.Loading())
    val genres = mutableStateGenres.asStateFlow()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getGenres() {
        viewModelScope.launch {
            movieRepository.getGenres().collect {
                mutableStateGenres.value = UiState.Success(it)
            }
        }
    }
}


// Represents different states for the LatestNews screen
sealed class HomeUiState {
    data class Success(val news: List<GenreEntity>): HomeUiState()
    data class Error(val exception: Throwable): HomeUiState()
}
