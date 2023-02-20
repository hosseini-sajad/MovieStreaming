package com.moviestreaming.repository

import com.moviestreaming.datasource.MovieNetworkDataSource
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieNetworkDataSource: MovieNetworkDataSource) {
    suspend fun getGenres() = movieNetworkDataSource.getGenres()
}