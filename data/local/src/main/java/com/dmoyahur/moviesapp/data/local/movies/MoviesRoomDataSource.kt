package com.dmoyahur.moviesapp.data.local.movies

import com.dmoyahur.moviesapp.data.local.LocalQueryHandler.query
import com.dmoyahur.moviesapp.data.local.movies.mapper.MovieDboMapper
import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesLocalDataSource
import com.dmoyahur.moviesapp.model.MovieBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRoomDataSource @Inject constructor(
    private val moviesDao: MoviesDao
) : MoviesLocalDataSource {

    override val movies: Flow<List<MovieBo>> =
        query { moviesDao.getPopularMovies().map { MovieDboMapper.mapToDomain(it) } }

    override fun findMovieById(id: Int): Flow<MovieBo?> {
        return query {
            moviesDao.findMovieById(id)
                .map { movie -> movie?.let { MovieDboMapper.mapToDomain(it) } }
        }
    }

    override suspend fun saveMovies(movies: List<MovieBo>) =
        query { moviesDao.saveMovies(MovieDboMapper.mapToDatabase(movies)) }
}