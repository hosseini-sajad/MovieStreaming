package com.moviestreaming.repository

import com.moviestreaming.data.model.TrendingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository() : MovieRepository {
    private var trending: MutableList<TrendingEntity> = mutableListOf()
    override suspend fun getTrending(): Flow<List<TrendingEntity>> {
        return flow {
            if (trending.isNotEmpty()) {
                emit(trending)
                return@flow
            }
            emit(emptyList())
        }
    }

    fun addTrending(lisOfTrending: List<TrendingEntity>) {
       trending.addAll(lisOfTrending)
    }
}