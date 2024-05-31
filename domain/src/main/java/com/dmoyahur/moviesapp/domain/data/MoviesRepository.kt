package com.dmoyahur.moviesapp.domain.data

import com.dmoyahur.moviesapp.domain.model.MovieBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class MoviesRepository(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
) {
    val movies: Flow<List<MovieBo>> =
        localDataSource.movies.onEach { localMovies ->
            if (localMovies.isEmpty()) {
                val remoteMovies = remoteDataSource.fetchPopularMovies()
                localDataSource.saveMovies(remoteMovies)
            }
        }

    val previousSearches: Flow<List<MovieBo>> = localDataSource.previousSearches

    fun findMovieById(id: Int): Flow<MovieBo> = localDataSource.findMovieById(id).filterNotNull()

    fun findMovieSearchById(id: Int): Flow<MovieBo> =
        localDataSource.findMovieSearchById(id).map { localMovie ->
            val result = localMovie ?: remoteDataSource.fetchMovieById(id)
            localDataSource.saveMovieSearch(result)
            result
        }.filterNotNull()

    suspend fun searchMovie(query: String): Flow<List<MovieBo>> =
        flow { emit(remoteDataSource.searchMovie(query)) }
}