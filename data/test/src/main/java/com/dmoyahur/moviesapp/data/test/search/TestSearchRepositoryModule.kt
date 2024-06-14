package com.dmoyahur.moviesapp.data.test.search

import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import com.dmoyahur.moviesapp.data.repository.search.di.SearchRepositoryModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SearchRepositoryModule::class]
)
internal interface TestSearchRepositoryModule {
    @Binds
    fun bindFakeSearchRepository(repository: FakeSearchRepository): SearchRepository
}