package com.dmoyahur.moviesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmoyahur.moviesapp.data.database.model.MovieDb
import com.dmoyahur.moviesapp.data.database.model.MovieSearchDb

@Database(entities = [MovieDb::class, MovieSearchDb::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}