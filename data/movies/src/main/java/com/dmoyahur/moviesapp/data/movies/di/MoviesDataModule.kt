package com.dmoyahur.moviesapp.data.movies.di

import com.dmoyahur.moviesapp.data.movies.database.MoviesRoomDataSource
import com.dmoyahur.moviesapp.data.movies.network.MoviesNetworkDataSource
import com.dmoyahur.moviesapp.domain.movies.data.MoviesLocalDataSource
import com.dmoyahur.moviesapp.domain.movies.data.MoviesRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MoviesDataModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: MoviesRoomDataSource): MoviesLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: MoviesNetworkDataSource): MoviesRemoteDataSource
}