package com.dmoyahur.moviesapp.data.search.network

import com.dmoyahur.core.model.MovieBo
import com.dmoyahur.moviesapp.core.data.network.mapper.MovieDtoMapper
import com.dmoyahur.moviesapp.domain.search.data.SearchRemoteDataSource
import javax.inject.Inject

class SearchNetworkDataSource @Inject constructor(
    private val searchService: SearchService
) : SearchRemoteDataSource {

    override suspend fun fetchMovieById(id: Int): MovieBo =
        searchService.fetchMovieById(id).let { MovieDtoMapper.mapToDomain(it) }

    override suspend fun searchMovie(query: String): List<MovieBo> =
        searchService.searchMovie(query)
            .results
            .map { MovieDtoMapper.mapToDomain(it) }
}