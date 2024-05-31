package com.dmoyahur.moviesapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmoyahur.moviesapp.data.database.model.MovieDb
import com.dmoyahur.moviesapp.data.database.model.MovieSearchDb
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movie")
    fun fetchPopularMovies(): Flow<List<MovieDb>>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun findMovieById(id: Int): Flow<MovieDb?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieDb>)

    @Query("SELECT * FROM MovieSearch ORDER BY timeStamp DESC")
    fun getMoviesSearch(): Flow<List<MovieSearchDb>>

    @Query("SELECT * FROM MovieSearch WHERE id = :id")
    fun findMovieSearchById(id: Int): Flow<MovieSearchDb?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieSearch(movie: MovieSearchDb)
}