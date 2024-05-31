package com.dmoyahur.moviesapp.data.search.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmoyahur.moviesapp.data.search.database.model.MovieSearchDb
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Query("SELECT * FROM MovieSearch ORDER BY timeStamp DESC")
    fun getMoviesSearch(): Flow<List<MovieSearchDb>>

    @Query("SELECT * FROM MovieSearch WHERE id = :id")
    fun findMovieSearchById(id: Int): Flow<MovieSearchDb?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieSearch(movie: MovieSearchDb)
}