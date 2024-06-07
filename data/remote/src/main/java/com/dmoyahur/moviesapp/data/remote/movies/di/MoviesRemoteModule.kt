package com.dmoyahur.moviesapp.data.remote.movies.di

import com.dmoyahur.moviesapp.data.remote.movies.MoviesNetworkApi
import com.dmoyahur.moviesapp.data.remote.movies.MoviesNetworkDataSource
import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MoviesRemoteModule {

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: MoviesNetworkDataSource): MoviesRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
internal object MoviesNetworkModule {

    @Provides
    @Singleton
    fun provideMoviesNetworkApi(retrofit: Retrofit): MoviesNetworkApi =
        retrofit.create(MoviesNetworkApi::class.java)
}

