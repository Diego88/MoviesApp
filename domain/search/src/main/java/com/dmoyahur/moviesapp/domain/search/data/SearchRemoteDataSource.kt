package com.dmoyahur.moviesapp.domain.search.data

import com.dmoyahur.moviesapp.core.model.MovieBo

interface SearchRemoteDataSource {
    suspend fun fetchMovieById(id: Int): MovieBo
    suspend fun searchMovie(query: String): List<MovieBo>
}