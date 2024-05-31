package com.dmoyahur.moviesapp.data.database

import com.dmoyahur.moviesapp.data.database.mapper.MovieDbMapper
import com.dmoyahur.moviesapp.data.database.mapper.MovieSearchDbMapper
import com.dmoyahur.moviesapp.domain.data.MoviesLocalDataSource
import com.dmoyahur.moviesapp.domain.model.MovieBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRoomDataSource(private val moviesDao: MoviesDao) : MoviesLocalDataSource {

    override val movies: Flow<List<MovieBo>> =
        moviesDao.fetchPopularMovies().map { MovieDbMapper.mapToDomain(it) }

    override val previousSearches: Flow<List<MovieBo>> =
        moviesDao.getMoviesSearch().map { MovieSearchDbMapper.mapToDomain(it) }

    override fun findMovieById(id: Int): Flow<MovieBo?> =
        moviesDao.findMovieById(id).map { movie -> movie?.let { MovieDbMapper.mapToDomain(it) } }

    override fun findMovieSearchById(id: Int): Flow<MovieBo?> =
        moviesDao.findMovieSearchById(id).map { movie -> movie?.let { MovieSearchDbMapper.mapToDomain(it) } }

    override suspend fun saveMovies(movies: List<MovieBo>) =
        moviesDao.saveMovies(MovieDbMapper.mapToDatabase(movies))

    override suspend fun saveMovieSearch(movie: MovieBo) =
        moviesDao.saveMovieSearch(MovieSearchDbMapper.mapToDatabase(movie))
}