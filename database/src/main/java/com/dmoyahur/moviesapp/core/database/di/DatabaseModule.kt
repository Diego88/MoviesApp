package com.dmoyahur.moviesapp.core.database.di

import android.app.Application
import androidx.room.Room
import com.dmoyahur.moviesapp.core.database.MoviesRoomDatabase
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
        MoviesRoomDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun provideMoviesDao(db: MoviesRoomDatabase) = db.moviesDao()

    @Provides
    fun provideSearchDao(db: MoviesRoomDatabase) = db.searchDao()
}