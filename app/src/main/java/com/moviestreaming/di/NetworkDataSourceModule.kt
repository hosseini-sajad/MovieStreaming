package com.moviestreaming.di

import com.moviestreaming.data.datasource.NetworkDataSourceImp
import com.moviestreaming.data.network.NetworkDataSource
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