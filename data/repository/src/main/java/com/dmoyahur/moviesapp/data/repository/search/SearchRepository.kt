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

    val previousSearches: Flow<List<MovieBo>> = localDataSource.previousSearches

    fun findMovieSearchById(id: Int): Flow<MovieBo> =
        localDataSource.findMovieSearchById(id).onEach { localMovie ->
            try {
                val remoteMovie = remoteDataSource.fetchMovieById(id)
                localDataSource.saveMovieSearch(remoteMovie)
            } catch (exception: Exception) {
                if (localMovie == null) throw exception
            }
        }.filterNotNull()

    suspend fun searchMovie(query: String): Flow<List<MovieBo>> =
        flow { emit(remoteDataSource.searchMovie(query)) }
}