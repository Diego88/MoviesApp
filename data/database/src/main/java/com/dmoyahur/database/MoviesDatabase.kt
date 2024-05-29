package com.dmoyahur.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmoyahur.database.model.MovieDb

@Database(entities = [MovieDb::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}