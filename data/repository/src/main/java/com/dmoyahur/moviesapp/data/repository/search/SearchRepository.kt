package com.dmoyahur.moviesapp.data.repository.search

import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchLocalDataSource
import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchRemoteDataSource
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.model.error.AsyncException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

interface SearchRepository {
    val previousSearches: Flow<List<MovieBo>>
    fun findMovieSearchById(id: Int): Flow<MovieBo>
    fun searchMovie(query: String): Flow<List<MovieBo>>
    suspend fun deleteMovieSearch(id: Int)
}

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val localDataSource: SearchLocalDataSource
) : SearchRepository {

    companion object {
        private const val MAX_HISTORY_SIZE = 12
    }

    override val previousSearches: Flow<List<MovieBo>> = localDataSource.previousSearches

    override fun findMovieSearchById(id: Int): Flow<MovieBo> =
        localDataSource.findMovieSearchById(id).onStart {
            try {
                val remoteMovie = remoteDataSource.fetchMovieById(id)
                saveMovieSearch(remoteMovie)
            } catch (exception: AsyncException) {
                if (localDataSource.findMovieSearchById(id).first() == null) throw exception
            }
        }.filterNotNull()

    override fun searchMovie(query: String): Flow<List<MovieBo>> =
        flow { emit(remoteDataSource.searchMovie(query)) }

    override suspend fun deleteMovieSearch(id: Int) {
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