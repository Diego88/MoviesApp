package com.dmoyahur.moviesapp.data.local.search.di

import com.dmoyahur.moviesapp.data.local.search.SearchRoomDataSource
import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SearchLocalModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: SearchRoomDataSource): SearchLocalDataSource
}