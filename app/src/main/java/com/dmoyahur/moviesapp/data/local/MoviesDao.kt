package com.dmoyahur.moviesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movie")
    fun fetchPopularMovies(): Flow<List<MovieDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movies: List<MovieDb>)
}