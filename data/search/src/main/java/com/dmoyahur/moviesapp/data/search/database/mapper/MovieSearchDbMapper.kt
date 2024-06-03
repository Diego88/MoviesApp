package com.dmoyahur.moviesapp.data.search.database.mapper

import com.dmoyahur.moviesapp.core.model.MovieBo
import com.dmoyahur.moviesapp.data.search.database.model.MovieSearchDb

object MovieSearchDbMapper {

    internal fun mapToDomain(movieSearchDb: MovieSearchDb): MovieBo {
        return with(movieSearchDb) {
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

    internal fun mapToDomain(moviesSearchDb: List<MovieSearchDb>): List<MovieBo> =
        moviesSearchDb.map { mapToDomain(it) }

    internal fun mapToDatabase(movieBo: MovieBo): MovieSearchDb {
        return with(movieBo) {
            MovieSearchDb(
                id = id,
                title = title,
                overview = overview,
                popularity = popularity,
                poster = poster,
                backdrop = backdrop,
                releaseDate = releaseDate,
                voteAverage = voteAverage,
                originalTitle = originalTitle,
                originalLanguage = originalLanguage,
                timeStamp = System.currentTimeMillis()
            )
        }
    }
}