package com.moviestreaming.data.source.network

import javax.inject.Inject

/**
 * Factory class to create SearchMoviesRemoteDataSource with query parameter
 */
class SearchMoviesRemoteDataSourceFactory @Inject constructor(
    private val apiService: ApiService
) {
    fun create(query: String): SearchMoviesRemoteDataSource {
        return SearchMoviesRemoteDataSource(apiService, query)
    }
}
