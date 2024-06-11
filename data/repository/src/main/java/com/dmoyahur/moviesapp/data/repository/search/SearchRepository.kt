package com.dmoyahur.moviesapp.data.repository.search

import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchLocalDataSource
import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchRemoteDataSource
import com.dmoyahur.moviesapp.model.MovieBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val localDataSource: SearchLocalDataSource,
) {

    companion object {
        private const val MAX_HISTORY_SIZE = 12
    }

    val previousSearches: Flow<List<MovieBo>> = localDataSource.previousSearches

    fun findMovieSearchById(id: Int): Flow<MovieBo> =
        localDataSource.findMovieSearchById(id).onEach { localMovie ->
            try {
                val remoteMovie = remoteDataSource.fetchMovieById(id)
                saveMovieSearch(remoteMovie)
            } catch (exception: Exception) {
                if (localMovie == null) throw exception
            }
        }.filterNotNull()

    suspend fun searchMovie(query: String): Flow<List<MovieBo>> =
        flow { emit(remoteDataSource.searchMovie(query)) }

    suspend fun deleteMovieSearch(id: Int) {
        localDataSource.deleteMovieSearch(id)
    }

    private suspend fun saveMovieSearch(movie: MovieBo) {
        localDataSource.saveMovieSearch(movie)

        val currentCount = localDataSource.getMoviesSearchCount()
        if (currentCount > MAX_HISTORY_SIZE) {
            val excessCount = currentCount - MAX_HISTORY_SIZE
            localDataSource.deleteOldestSearches(excessCount)
        }
    }
}