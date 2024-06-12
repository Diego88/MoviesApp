package com.dmoyahur.moviesapp.data.local.di

import android.app.Application
import androidx.room.Room
import com.dmoyahur.moviesapp.data.local.AppRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
internal object TestDatabaseModule {

    @Provides
    @Singleton
    @Named("test_db")
    fun provideTestDatabase(app: Application) =
        Room.inMemoryDatabaseBuilder(app, AppRoomDatabase::class.java).build()

    @Provides
    @Named("test_moviesDao")
    fun provideTestMoviesDao(@Named("test_db") db: AppRoomDatabase) = db.moviesDao()

    @Provides
    @Named("test_searchDao")
    fun provideTestSearchDao(@Named("test_db") db: AppRoomDatabase) = db.searchDao()
}