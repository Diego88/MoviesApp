package com.dmoyahur.moviesapp.data.movies.network

import com.dmoyahur.core.model.MovieBo
import com.dmoyahur.moviesapp.core.data.network.mapper.MovieDtoMapper
import com.dmoyahur.moviesapp.domain.movies.data.MoviesRemoteDataSource
import javax.inject.Inject

class MoviesNetworkDataSource @Inject constructor(
    private val moviesService: MoviesService
) : MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(): List<MovieBo> {
        return moviesService.fetchPopularMovies()
            .results
            .map { MovieDtoMapper.mapToDomain(it) }
    }
}