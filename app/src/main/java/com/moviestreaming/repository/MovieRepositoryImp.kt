package com.moviestreaming.repository

import com.moviestreaming.data.source.NetworkDataSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val networkDataSource: NetworkDataSource) :
    MovieRepository {
    override suspend fun getTrending() = networkDataSource.getTrending().map {
        it.map { trendingMovie -> trendingMovie.toEntity() }
    }.flowOn(IO)
}