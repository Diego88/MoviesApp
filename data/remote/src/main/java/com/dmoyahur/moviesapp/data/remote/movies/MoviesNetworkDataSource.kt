package com.dmoyahur.moviesapp.data.remote.movies

import com.dmoyahur.moviesapp.data.remote.movies.mapper.MovieDtoMapper
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.data.remote.RemoteRequestHandler.request
import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesRemoteDataSource
import javax.inject.Inject

class MoviesNetworkDataSource @Inject constructor(
    private val moviesNetworkApi: MoviesNetworkApi
) : MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(): List<MovieBo> {
        return request {
            moviesNetworkApi.fetchPopularMovies()
                .results
                .map { MovieDtoMapper.mapToDomain(it) }
        }
    }
}