package com.dmoyahur.moviesapp.data.remote.search.di

import com.dmoyahur.moviesapp.data.remote.search.SearchNetworkApi
import com.dmoyahur.moviesapp.data.remote.search.SearchNetworkDataSource
import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SearchRemoteModule {

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: SearchNetworkDataSource): SearchRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object SearchNetworkModule {

    @Provides
    @Singleton
    fun provideSearchNetworkApi(retrofit: Retrofit): SearchNetworkApi =
        retrofit.create(SearchNetworkApi::class.java)
}