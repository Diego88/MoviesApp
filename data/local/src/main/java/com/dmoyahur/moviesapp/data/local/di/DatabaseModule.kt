package com.dmoyahur.moviesapp.data.local.di

import android.app.Application
import androidx.room.Room
import com.dmoyahur.moviesapp.data.local.AppRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        AppRoomDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun provideMoviesDao(db: AppRoomDatabase) = db.moviesDao()

    @Provides
    fun provideSearchDao(db: AppRoomDatabase) = db.searchDao()
}