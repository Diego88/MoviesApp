package com.dmoyahur.moviesapp.data.local.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmoyahur.moviesapp.data.local.search.dbo.MovieSearchDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Query("SELECT * FROM MovieSearch ORDER BY timeStamp DESC")
    fun getMoviesSearch(): Flow<List<MovieSearchDbo>>

    @Query("SELECT * FROM MovieSearch WHERE id = :id")
    fun findMovieSearchById(id: Int): Flow<MovieSearchDbo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieSearch(movie: MovieSearchDbo)
}