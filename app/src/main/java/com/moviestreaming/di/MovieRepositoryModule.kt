package com.moviestreaming.di

import com.moviestreaming.repository.MovieRepository
import com.moviestreaming.repository.MovieRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepositoryModule {
    @Binds
    abstract fun bindMovieRepository(movieRepositoryImp: MovieRepositoryImp) : MovieRepository
}