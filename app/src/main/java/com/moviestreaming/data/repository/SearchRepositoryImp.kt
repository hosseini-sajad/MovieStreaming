package com.moviestreaming.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.source.network.SearchMoviesRemoteDataSourceFactory
import com.moviestreaming.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImp @Inject constructor(
    private val searchMoviesRemoteDataSourceFactory: SearchMoviesRemoteDataSourceFactory
): SearchRepository {
    override fun searchByName(query: String): Flow<PagingData<TopRateMovieEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                searchMoviesRemoteDataSourceFactory.create(query)
            }
        ).flow.flowOn(Dispatchers.IO)
    }

//    override fun searchByDirector(query: String): Flow<List<SearchDto>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun searchByGenre(query: String): Flow<List<SearchDto>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun searchByYear(query: String): Flow<List<SearchDto>> {
//        TODO("Not yet implemented")
//    }
}