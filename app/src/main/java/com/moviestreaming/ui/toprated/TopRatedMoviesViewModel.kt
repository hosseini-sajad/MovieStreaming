package com.moviestreaming.ui.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moviestreaming.core.usecase.GetTopRatedMoviesUseCase
import com.moviestreaming.data.model.TopRateMovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : ViewModel() {

    fun getTopRatedMovies(): Flow<PagingData<TopRateMovieEntity>> {
        return getTopRatedMoviesUseCase().cachedIn(viewModelScope)
    }
}
