package com.dmoyahur.moviesapp.data.repository.movies.datasource

import com.dmoyahur.moviesapp.model.MovieBo

interface MoviesRemoteDataSource {
    suspend fun fetchPopularMovies(): List<MovieBo>
}