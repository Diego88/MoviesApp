package com.dmoyahur.moviesapp.di

import com.dmoyahur.moviesapp.data.remote.di.BaseUrl
import com.dmoyahur.moviesapp.data.remote.di.BaseUrlModule
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
class AndroidTestBaseUrlModule {

    @Provides
    @Singleton
    @BaseUrl
    fun provideTestUrl(): String = "http://localhost:8080/"
}