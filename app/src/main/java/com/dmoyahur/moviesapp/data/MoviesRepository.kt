package com.dmoyahur.moviesapp.data

import com.dmoyahur.moviesapp.data.local.MoviesLocalDataSource
import com.dmoyahur.moviesapp.data.remote.MoviesRemoteDataSource
import com.dmoyahur.moviesapp.domain.MovieBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach

class MoviesRepository(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
) {
    val movies: Flow<List<MovieBo>> = localDataSource.movies.onEach { localMovies ->
        if (localMovies.isEmpty()) {
            val remoteMovies = remoteDataSource.fetchPopularMovies()
            localDataSource.save(remoteMovies)
        }
    }

    fun findMovieById(id: Int): Flow<MovieBo> =
        localDataSource.findMovieById(id).onEach { localMovie ->
            if (localMovie == null) {
                val remoteMovie = remoteDataSource.findMovieById(id)
                localDataSource.save(listOf(remoteMovie))
            }
        }.filterNotNull()
}