package com.dmoyahur.moviesapp.data.remote.search

import com.dmoyahur.moviesapp.data.remote.RemoteRequestHandler.request
import com.dmoyahur.moviesapp.data.remote.movies.mapper.MovieDtoMapper
import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchRemoteDataSource
import com.dmoyahur.moviesapp.model.MovieBo
import javax.inject.Inject

class SearchNetworkDataSource @Inject constructor(
    private val searchNetworkApi: SearchNetworkApi
) : SearchRemoteDataSource {

    override suspend fun fetchMovieById(id: Int): MovieBo =
        request { searchNetworkApi.fetchMovieById(id).let { MovieDtoMapper.mapToDomain(it) } }

    override suspend fun searchMovie(query: String): List<MovieBo> {
        return request {
            searchNetworkApi.searchMovie(query)
                .results
                .map { MovieDtoMapper.mapToDomain(it) }
        }
    }
}