package com.dmoyahur.moviesapp.data.repository.movies.di

import com.dmoyahur.moviesapp.data.repository.movies.MoviesRepository
import com.dmoyahur.moviesapp.data.repository.movies.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesRepositoryModule {

    @Binds
    abstract fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository
}