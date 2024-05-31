package com.dmoyahur.moviesapp.data.movies.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmoyahur.moviesapp.data.movies.database.model.MovieDb
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movie")
    fun fetchPopularMovies(): Flow<List<MovieDb>>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun findMovieById(id: Int): Flow<MovieDb?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieDb>)
}