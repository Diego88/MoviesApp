package com.dmoyahur.moviesapp.data.repository.search.di

import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import com.dmoyahur.moviesapp.data.repository.search.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchRepositoryModule {

    @Binds
    abstract fun bindSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository
}