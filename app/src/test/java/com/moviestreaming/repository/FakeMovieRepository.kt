package com.moviestreaming.repository

import com.moviestreaming.data.model.TrendingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository() : MovieRepository {
    private val trending: MutableList<TrendingEntity>? = null
    override suspend fun getTrending(): Flow<List<TrendingEntity>> {
        return flow {
            if (trending != null) {
                emit(trending)
                return@flow
            }
            emptyFlow<List<TrendingEntity>>()
        }
    }

    fun addTrending(lisOfTrending: List<TrendingEntity>) {
       trending?.addAll(lisOfTrending)
    }
}