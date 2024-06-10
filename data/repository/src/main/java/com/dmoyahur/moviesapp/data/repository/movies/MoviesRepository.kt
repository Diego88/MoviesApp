package com.dmoyahur.moviesapp.data.repository.movies

import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesLocalDataSource
import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesRemoteDataSource
import com.dmoyahur.moviesapp.model.MovieBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
) {

    val movies: Flow<List<MovieBo>> = localDataSource.movies.onStart {
        try {
            val remoteMovies = remoteDataSource.fetchPopularMovies()
            localDataSource.saveMovies(remoteMovies)
        } catch (e: Exception) {
            if (localDataSource.movies.first().isEmpty()) throw e
        }
    }

    fun findMovieById(id: Int): Flow<MovieBo> = localDataSource.findMovieById(id).filterNotNull()
}