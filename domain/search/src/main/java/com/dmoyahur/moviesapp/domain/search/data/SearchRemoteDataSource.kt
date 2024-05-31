package com.dmoyahur.moviesapp.domain.search.data

import com.dmoyahur.core.model.MovieBo

interface SearchRemoteDataSource {
    suspend fun fetchMovieById(id: Int): MovieBo
    suspend fun searchMovie(query: String): List<MovieBo>
}