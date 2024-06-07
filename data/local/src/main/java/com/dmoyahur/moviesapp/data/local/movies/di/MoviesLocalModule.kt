package com.dmoyahur.moviesapp.data.local.movies.di

import com.dmoyahur.moviesapp.data.local.movies.MoviesRoomDataSource
import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MoviesLocalModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: MoviesRoomDataSource): MoviesLocalDataSource
}