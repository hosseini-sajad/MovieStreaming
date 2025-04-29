package com.moviestreaming.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviestreaming.data.model.CreditsEntity.CastEntity
import com.moviestreaming.data.model.CreditsEntity.CrewEntity
import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = savedStateHandle["movieId"] ?: -1

    val detailUiState: StateFlow<DetailUiState> = combine(
        movieRepository.getMovieDetails(movieId),
        movieRepository.getSimilarMovies(movieId),
        movieRepository.getMovieCredits(movieId)
    ) { movieDetail, similarMovies, movieCredits ->
        DetailUiState(
            movieDetail = movieDetail,
            similarMovies = similarMovies,
            castMovie = movieCredits.casts.take(10),
            crewMovie = getDirectorsNameFormatted(
                movieCredits.crews.filter
            { it.job == "Director" }.take(2)
            ),
            isLoading = false,
            errorMessage = null
        )
    }
        .catch { e ->
            emit(
                DetailUiState(
                    isLoading = false,
                    errorMessage = e.message ?: "An error occurred"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailUiState(isLoading = true)
        )


    private fun getDirectorsNameFormatted(data: List<CrewEntity>): CharSequence? {
        val separator = ", "
        val directorsName = data.map {
            it.originalName
        }
        return java.lang.String.join(separator, directorsName)
    }

}

data class DetailUiState(
    val movieDetail: MovieDetailEntity? = null,
    val similarMovies: List<TopRateMovieEntity>? = null,
    val castMovie: List<CastEntity>? = null,
    val crewMovie: CharSequence? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)