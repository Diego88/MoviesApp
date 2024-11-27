package com.dmoyahur.moviesapp.data.repository.movies.datasource

import com.dmoyahur.moviesapp.model.MovieBo

class FakeMoviesRemoteDataSource(
    private val movies: List<MovieBo> = emptyList()
) : MoviesRemoteDataSource {
    override suspend fun fetchPopularMovies(): List<MovieBo> = movies
}