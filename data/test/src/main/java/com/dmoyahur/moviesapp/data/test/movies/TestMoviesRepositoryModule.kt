package com.dmoyahur.moviesapp.data.test.movies

import com.dmoyahur.moviesapp.data.repository.movies.MoviesRepository
import com.dmoyahur.moviesapp.data.repository.movies.di.MoviesRepositoryModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MoviesRepositoryModule::class]
)
internal interface TestMoviesRepositoryModule {
    @Binds
    fun bindFakeMoviesRepository(repository: FakeMoviesRepository): MoviesRepository
}