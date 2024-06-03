package com.dmoyahur.moviesapp.data.search.database

import com.dmoyahur.moviesapp.core.model.MovieBo
import com.dmoyahur.moviesapp.data.search.database.mapper.MovieSearchDbMapper
import com.dmoyahur.moviesapp.domain.search.data.SearchLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRoomDataSource @Inject constructor(
    private val moviesDao: SearchDao
) : SearchLocalDataSource {

    override val previousSearches: Flow<List<MovieBo>> =
        moviesDao.getMoviesSearch().map { MovieSearchDbMapper.mapToDomain(it) }

    override fun findMovieSearchById(id: Int): Flow<MovieBo?> =
        moviesDao.findMovieSearchById(id)
            .map { movie -> movie?.let { MovieSearchDbMapper.mapToDomain(it) } }

    override suspend fun saveMovieSearch(movie: MovieBo) =
        moviesDao.saveMovieSearch(MovieSearchDbMapper.mapToDatabase(movie))
}