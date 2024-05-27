package com.dmoyahur.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieDb::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}