package com.dmoyahur.domain.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach

class MoviesRepository(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
) {
    val movies: Flow<List<com.dmoyahur.domain.model.MovieBo>> =
        localDataSource.movies.onEach { localMovies ->
            if (localMovies.isEmpty()) {
                val remoteMovies = remoteDataSource.fetchPopularMovies()
                localDataSource.save(remoteMovies)
            }
        }

    fun findMovieById(id: Int): Flow<com.dmoyahur.domain.model.MovieBo> =
        localDataSource.findMovieById(id).onEach { localMovie ->
            if (localMovie == null) {
                val remoteMovie = remoteDataSource.findMovieById(id)
                localDataSource.save(listOf(remoteMovie))
            }
        }.filterNotNull()
}