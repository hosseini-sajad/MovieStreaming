package com.moviestreaming.repository

import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository() : MovieRepository {
    private var trending: MutableList<TrendingEntity> = mutableListOf()
    override suspend fun getTrending(): Flow<UiState<List<TrendingEntity>?>> {
        return flow {
            if (trending.isNotEmpty()) {
                emit(UiState.Success(trending))
                return@flow
            }
            emit(UiState.Error("Server problem"))
        }
    }

    fun addTrending(lisOfTrending: List<TrendingEntity>) {
        trending.addAll(lisOfTrending)
    }
}