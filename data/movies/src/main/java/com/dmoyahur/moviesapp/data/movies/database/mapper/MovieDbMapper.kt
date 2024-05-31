package com.dmoyahur.moviesapp.data.movies.database.mapper

import com.dmoyahur.core.model.MovieBo
import com.dmoyahur.moviesapp.data.movies.database.model.MovieDb

object MovieDbMapper {

    internal fun mapToDomain(movieDb: MovieDb): MovieBo {
        return with(movieDb) {
            MovieBo(
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
        }
    }

    internal fun mapToDomain(moviesDb: List<MovieDb>): List<MovieBo> =
        moviesDb.map { mapToDomain(it) }

    private fun mapToDatabase(movieBo: MovieBo): MovieDb {
        return with(movieBo) {
            MovieDb(
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
        }
    }

    internal fun mapToDatabase(moviesBo: List<MovieBo>): List<MovieDb> =
        moviesBo.map { mapToDatabase(it) }
}