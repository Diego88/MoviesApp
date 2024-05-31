package com.dmoyahur.moviesapp.data.movies.database

import com.dmoyahur.core.model.MovieBo
import com.dmoyahur.moviesapp.data.movies.database.mapper.MovieDbMapper
import com.dmoyahur.moviesapp.domain.movies.data.MoviesLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRoomDataSource(private val moviesDao: MoviesDao) : MoviesLocalDataSource {

    override val movies: Flow<List<MovieBo>> =
        moviesDao.fetchPopularMovies().map { MovieDbMapper.mapToDomain(it) }

    override fun findMovieById(id: Int): Flow<MovieBo?> =
        moviesDao.findMovieById(id).map { movie -> movie?.let { MovieDbMapper.mapToDomain(it) } }

    override suspend fun saveMovies(movies: List<MovieBo>) =
        moviesDao.saveMovies(MovieDbMapper.mapToDatabase(movies))
}