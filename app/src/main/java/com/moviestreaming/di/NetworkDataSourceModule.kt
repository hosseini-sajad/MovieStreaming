package com.moviestreaming.di

import com.moviestreaming.data.source.NetworkDataSource
import com.moviestreaming.data.source.network.NetworkDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {
    @Binds
    abstract fun bindNetworkDataSource(networkDataSourceImp: NetworkDataSourceImp): NetworkDataSource
}