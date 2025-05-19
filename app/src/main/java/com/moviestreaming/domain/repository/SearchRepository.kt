package com.moviestreaming.domain.repository

import androidx.paging.PagingData
import com.moviestreaming.data.model.TopRateMovieEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchByName(query: String): Flow<PagingData<TopRateMovieEntity>>
//    fun searchByDirector(query: String): Flow<List<SearchDto>>
//    fun searchByGenre(query: String): Flow<List<SearchDto>>
//    fun searchByYear(query: String): Flow<List<SearchDto>>
}