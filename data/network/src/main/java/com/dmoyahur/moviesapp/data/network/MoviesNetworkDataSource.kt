package com.dmoyahur.moviesapp.data.network

import com.dmoyahur.moviesapp.data.network.mapper.MovieDtoMapper
import com.dmoyahur.moviesapp.domain.data.MoviesRemoteDataSource
import com.dmoyahur.moviesapp.domain.model.MovieBo

class MoviesNetworkDataSource : MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(): List<MovieBo> {
        return MoviesClient.instance.fetchPopularMovies()
            .results
            .map { MovieDtoMapper.mapToDomain(it) }
    }

    override suspend fun fetchMovieById(id: Int): MovieBo =
        MoviesClient.instance.fetchMovieById(id).let { MovieDtoMapper.mapToDomain(it) }

    override suspend fun searchMovie(query: String): List<MovieBo> =
        MoviesClient.instance.searchMovie(query)
            .results
            .map { MovieDtoMapper.mapToDomain(it) }
}