package com.moviestreaming.di

import com.moviestreaming.domain.repository.MovieRepository
import com.moviestreaming.data.repository.MovieRepositoryImp
import com.moviestreaming.data.repository.SearchRepositoryImp
import com.moviestreaming.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepositoryModule {
    @Binds
    abstract fun bindMovieRepository(movieRepositoryImp: MovieRepositoryImp) : MovieRepository

    @Binds
    abstract fun bindSearchRepository(searchRepositoryImp: SearchRepositoryImp): SearchRepository
}