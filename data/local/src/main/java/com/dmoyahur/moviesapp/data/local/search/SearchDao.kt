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

    @Query("DELETE FROM MovieSearch WHERE id = :id")
    suspend fun deleteMovieSearch(id: Int)

    @Query("SELECT COUNT(*) FROM MovieSearch")
    suspend fun getMoviesSearchCount(): Int

    @Query("DELETE FROM MovieSearch WHERE id IN (SELECT id FROM MovieSearch ORDER BY timestamp ASC LIMIT :excessCount)")
    suspend fun deleteOldestSearches(excessCount: Int)
}