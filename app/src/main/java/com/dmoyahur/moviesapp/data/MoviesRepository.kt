package com.dmoyahur.moviesapp.data

import com.dmoyahur.moviesapp.data.remote.MoviesRemoteDataSource
import com.dmoyahur.moviesapp.domain.MovieBo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val remoteDataSource: MoviesRemoteDataSource
) {
    suspend fun fetchPopularMovies(): List<MovieBo> = withContext(Dispatchers.IO) {
        remoteDataSource.fetchPopularMovies()
    }
}