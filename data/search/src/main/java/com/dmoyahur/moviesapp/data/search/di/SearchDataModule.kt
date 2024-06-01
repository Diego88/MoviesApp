package com.dmoyahur.moviesapp.data.search.di

import com.dmoyahur.moviesapp.data.search.database.SearchRoomDataSource
import com.dmoyahur.moviesapp.data.search.network.SearchNetworkDataSource
import com.dmoyahur.moviesapp.domain.search.data.SearchLocalDataSource
import com.dmoyahur.moviesapp.domain.search.data.SearchRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SearchDataModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: SearchRoomDataSource): SearchLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: SearchNetworkDataSource): SearchRemoteDataSource
}