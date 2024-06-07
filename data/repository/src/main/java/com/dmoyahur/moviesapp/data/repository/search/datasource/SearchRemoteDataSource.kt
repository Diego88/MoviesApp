package com.dmoyahur.moviesapp.data.repository.search.datasource

import com.dmoyahur.moviesapp.model.MovieBo

interface SearchRemoteDataSource {
    suspend fun fetchMovieById(id: Int): MovieBo
    suspend fun searchMovie(query: String): List<MovieBo>
}