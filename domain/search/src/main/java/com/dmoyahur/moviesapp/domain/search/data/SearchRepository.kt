package com.dmoyahur.moviesapp.domain.search.data

import com.dmoyahur.core.model.MovieBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val localDataSource: SearchLocalDataSource,
) {

    val previousSearches: Flow<List<MovieBo>> = localDataSource.previousSearches

    fun findMovieSearchById(id: Int): Flow<MovieBo> =
        localDataSource.findMovieSearchById(id).map { localMovie ->
            val result = localMovie ?: remoteDataSource.fetchMovieById(id)
            localDataSource.saveMovieSearch(result)
            result
        }.filterNotNull()

    suspend fun searchMovie(query: String): Flow<List<MovieBo>> =
        flow { emit(remoteDataSource.searchMovie(query)) }
}