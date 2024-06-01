package com.dmoyahur.moviesapp.core.network.di

import com.dmoyahur.moviesapp.core.network.RetrofitClient
import com.dmoyahur.moviesapp.data.movies.network.MoviesService
import com.dmoyahur.moviesapp.data.search.network.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideMoviesService(): MoviesService = RetrofitClient().moviesInstance

    @Provides
    @Singleton
    fun provideSearchService(): SearchService = RetrofitClient().searchInstance
}