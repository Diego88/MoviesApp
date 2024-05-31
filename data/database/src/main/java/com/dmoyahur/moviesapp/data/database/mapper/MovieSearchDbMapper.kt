package com.dmoyahur.moviesapp.data.database.mapper

import com.dmoyahur.moviesapp.data.database.model.MovieSearchDb
import com.dmoyahur.moviesapp.domain.model.MovieBo

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