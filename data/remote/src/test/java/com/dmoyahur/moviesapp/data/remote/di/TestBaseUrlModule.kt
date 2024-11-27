package com.dmoyahur.moviesapp.data.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [BaseUrlModule::class]
)
@Module
class TestBaseUrlModule {

    @Provides
    @Singleton
    @BaseUrl
    fun provideTestUrl(): String = "http://localhost:8080/"
}