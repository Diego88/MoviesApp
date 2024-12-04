package com.dmoyahur.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmoyahur.moviesapp.data.local.movies.MoviesDao
import com.dmoyahur.moviesapp.data.local.movies.dbo.MovieDbo
import com.dmoyahur.moviesapp.data.local.search.SearchDao
import com.dmoyahur.moviesapp.data.local.search.dbo.MovieSearchDbo

@Database(entities = [MovieDbo::class, MovieSearchDbo::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun searchDao(): SearchDao
}