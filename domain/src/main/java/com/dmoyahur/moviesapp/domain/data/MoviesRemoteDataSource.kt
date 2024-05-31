package com.dmoyahur.moviesapp.domain.data

import com.dmoyahur.moviesapp.domain.model.MovieBo

interface MoviesRemoteDataSource {
    suspend fun fetchPopularMovies(): List<MovieBo>
    suspend fun fetchMovieById(id: Int): MovieBo
    suspend fun searchMovie(query: String): List<MovieBo>
}