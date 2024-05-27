package com.dmoyahur.moviesapp.data.local

import com.dmoyahur.moviesapp.domain.MovieBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesLocalDataSource(private val moviesDao: MoviesDao) {

    val movies: Flow<List<MovieBo>> = moviesDao.fetchPopularMovies().map { it.toBo() }

    suspend fun save(movies: List<MovieBo>) = moviesDao.save(movies.toDb())
}

private fun MovieDb.toBo() = MovieBo(
    id = id,
    title = title,
    overview = overview,
    popularity = popularity,
    poster = poster,
    backdrop = backdrop,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage
)

private fun List<MovieDb>.toBo() = map { it.toBo() }

private fun MovieBo.toDb() = MovieDb(
    id = id,
    title = title,
    overview = overview,
    popularity = popularity,
    poster = poster,
    backdrop = backdrop,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage
)

private fun List<MovieBo>.toDb() = map { it.toDb() }