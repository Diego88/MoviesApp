package com.dmoyahur.moviesapp.domain.movies.data

import com.dmoyahur.moviesapp.core.model.MovieBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MoviesRepository @Inject constructor(
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

    fun findMovieById(id: Int): Flow<MovieBo> = localDataSource.findMovieById(id).filterNotNull()
}