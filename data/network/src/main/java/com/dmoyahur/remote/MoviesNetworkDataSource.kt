package com.dmoyahur.remote

import com.dmoyahur.domain.data.MoviesRemoteDataSource
import com.dmoyahur.domain.model.MovieBo
import com.dmoyahur.remote.mapper.MovieDtoMapper

class MoviesNetworkDataSource: MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(): List<MovieBo> {
        return MoviesClient.instance.fetchPopularMovies()
            .results
            .map { MovieDtoMapper.mapToDomain(it) }
    }

    override suspend fun findMovieById(id: Int): MovieBo =
        MoviesClient.instance.fetchMovieById(id).let { MovieDtoMapper.mapToDomain(it) }
}