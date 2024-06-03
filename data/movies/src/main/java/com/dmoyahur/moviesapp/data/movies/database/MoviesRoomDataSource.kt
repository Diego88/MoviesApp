package com.dmoyahur.moviesapp.data.movies.database

import com.dmoyahur.moviesapp.core.model.MovieBo
import com.dmoyahur.moviesapp.data.movies.database.mapper.MovieDbMapper
import com.dmoyahur.moviesapp.domain.movies.data.MoviesLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRoomDataSource @Inject constructor(
    private val moviesDao: MoviesDao
) : MoviesLocalDataSource {

    override val movies: Flow<List<MovieBo>> =
        moviesDao.getPopularMovies().map { MovieDbMapper.mapToDomain(it) }

    override fun findMovieById(id: Int): Flow<MovieBo?> =
        moviesDao.findMovieById(id).map { movie -> movie?.let { MovieDbMapper.mapToDomain(it) } }

    override suspend fun saveMovies(movies: List<MovieBo>) =
        moviesDao.saveMovies(MovieDbMapper.mapToDatabase(movies))
}