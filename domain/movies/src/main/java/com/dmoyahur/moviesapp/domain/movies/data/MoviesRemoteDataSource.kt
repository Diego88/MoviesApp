package com.dmoyahur.moviesapp.domain.movies.data

import com.dmoyahur.moviesapp.core.model.MovieBo

interface MoviesRemoteDataSource {
    suspend fun fetchPopularMovies(): List<MovieBo>
}