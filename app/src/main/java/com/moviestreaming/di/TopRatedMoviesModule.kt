package com.moviestreaming.di

import com.moviestreaming.data.source.network.ApiService
import com.moviestreaming.data.source.network.TopRatedMoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TopRatedMoviesModule {

    @Provides
    @Singleton
    fun provideTopRatedMoviesRemoteDataSource(
        apiService: ApiService
    ): TopRatedMoviesRemoteDataSource {
        return TopRatedMoviesRemoteDataSource(apiService)
    }
}
