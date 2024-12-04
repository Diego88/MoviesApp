package com.dmoyahur.moviesapp.data.repository.movies

import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesLocalDataSource
import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesRemoteDataSource
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.model.error.AsyncException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

interface MoviesRepository {
    val movies: Flow<List<MovieBo>>
    fun findMovieById(id: Int): Flow<MovieBo>
}

class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource
) : MoviesRepository {

    override val movies: Flow<List<MovieBo>> = localDataSource.movies.onStart {
        try {
            val remoteMovies = remoteDataSource.fetchPopularMovies()
            localDataSource.saveMovies(remoteMovies)
        } catch (e: AsyncException) {
            if (localDataSource.movies.first().isEmpty()) throw e
        }
    }

    override fun findMovieById(id: Int): Flow<MovieBo> =
        localDataSource.findMovieById(id).filterNotNull()
}