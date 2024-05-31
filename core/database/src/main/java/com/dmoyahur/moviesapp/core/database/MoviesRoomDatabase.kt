package com.dmoyahur.moviesapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmoyahur.moviesapp.data.movies.database.MoviesDao
import com.dmoyahur.moviesapp.data.movies.database.model.MovieDb
import com.dmoyahur.moviesapp.data.search.database.SearchDao
import com.dmoyahur.moviesapp.data.search.database.model.MovieSearchDb


@Database(entities = [MovieDb::class, MovieSearchDb::class], version = 1, exportSchema = false)
abstract class MoviesRoomDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun searchDao(): SearchDao
}