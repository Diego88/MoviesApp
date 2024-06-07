package com.dmoyahur.moviesapp.data.local.search

import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.data.local.search.mapper.MovieSearchDboMapper
import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRoomDataSource @Inject constructor(
    private val moviesDao: SearchDao
) : SearchLocalDataSource {

    override val previousSearches: Flow<List<MovieBo>> =
        moviesDao.getMoviesSearch().map { MovieSearchDboMapper.mapToDomain(it) }

    override fun findMovieSearchById(id: Int): Flow<MovieBo?> =
        moviesDao.findMovieSearchById(id)
            .map { movie -> movie?.let { MovieSearchDboMapper.mapToDomain(it) } }

    override suspend fun saveMovieSearch(movie: MovieBo) =
        moviesDao.saveMovieSearch(MovieSearchDboMapper.mapToDatabase(movie))
}