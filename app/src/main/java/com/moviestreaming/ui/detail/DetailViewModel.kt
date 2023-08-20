package com.moviestreaming.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviestreaming.data.model.CreditsEntity.CastEntity
import com.moviestreaming.data.model.CreditsEntity.CrewEntity
import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.data.model.TopRateMovieEntity
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

    private val mutableStateSimilarMovies = MutableStateFlow<UiState<List<TopRateMovieEntity>>>(UiState.Loading())
    val similarMovies: StateFlow<UiState<List<TopRateMovieEntity>>> = mutableStateSimilarMovies

    private val mutableStateCastMovie = MutableStateFlow<UiState<List<CastEntity>>>(UiState.Loading())
    val castMovie: StateFlow<UiState<List<CastEntity>>> = mutableStateCastMovie

    private val mutableStateCrewMovie = MutableStateFlow<UiState<List<CrewEntity>>>(UiState.Loading())
    val crewMovie: StateFlow<UiState<List<CrewEntity>>> = mutableStateCrewMovie

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieId).collect {
                when (it) {
                    is Result.Success -> mutableStateMovieDetail.value = UiState.Success(it.data)
                    is Result.Error -> mutableStateMovieDetail.value = UiState.Error(it.message)
                    else -> {}
                }
            }
        }
    }

    fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            movieRepository.getSimilarMovies(movieId).collect {
                when (it) {
                    is Result.Success -> mutableStateSimilarMovies.value = UiState.Success(it.data.take(5))
                    is Result.Error -> mutableStateSimilarMovies.value = UiState.Error(it.message)
                    else -> {}
                }
            }
        }
    }

    fun getMovieCredits(movieId: Int) {
        viewModelScope.launch {
            movieRepository.getMovieCredits(movieId).collect {
                when (it) {
                    is Result.Success -> {
                        mutableStateCastMovie.value = UiState.Success(it.data.casts.take(10))
                        mutableStateCrewMovie.value = UiState.Success(it.data.crews.filter { crew -> crew.job == "Director" }.take(2))
                    }
                    is Result.Error -> mutableStateCastMovie.value = UiState.Error(it.message)
                    else -> {}
                }
            }
        }
    }
}