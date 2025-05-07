package com.moviestreaming.di

import com.moviestreaming.data.source.network.ApiService
import com.moviestreaming.data.source.network.PopularMoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PopularMoviesModule {
    @Provides
    @Singleton
    fun providePopularMoviesRemoteDataSource(apiService: ApiService): PopularMoviesRemoteDataSource {
        return PopularMoviesRemoteDataSource(apiService)
    }
}