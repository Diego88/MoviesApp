package com.dmoyahur.domain.data

import com.dmoyahur.domain.model.MovieBo

interface MoviesRemoteDataSource {
    suspend fun fetchPopularMovies(): List<MovieBo>
    suspend fun findMovieById(id: Int): MovieBo
}