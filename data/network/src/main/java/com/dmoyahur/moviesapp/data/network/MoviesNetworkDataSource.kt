package com.dmoyahur.moviesapp.data.network

import com.dmoyahur.moviesapp.domain.data.MoviesRemoteDataSource
import com.dmoyahur.moviesapp.domain.model.MovieBo
import com.dmoyahur.moviesapp.data.network.mapper.MovieDtoMapper

class MoviesNetworkDataSource: MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(): List<MovieBo> {
        return MoviesClient.instance.fetchPopularMovies()
            .results
            .map { MovieDtoMapper.mapToDomain(it) }
    }

    override suspend fun findMovieById(id: Int): MovieBo =
        MoviesClient.instance.fetchMovieById(id).let { MovieDtoMapper.mapToDomain(it) }
}