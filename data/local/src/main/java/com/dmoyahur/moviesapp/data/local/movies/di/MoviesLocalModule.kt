package com.dmoyahur.moviesapp.data.local.movies.di

import com.dmoyahur.moviesapp.data.local.AppRoomDatabase
import com.dmoyahur.moviesapp.data.local.movies.MoviesRoomDataSource
import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MoviesLocalModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: MoviesRoomDataSource): MoviesLocalDataSource
}

@Module
@InstallIn(SingletonComponent::class)
internal object MoviesDaoModule {
    @Provides
    fun provideMoviesDao(db: AppRoomDatabase) = db.moviesDao()
}