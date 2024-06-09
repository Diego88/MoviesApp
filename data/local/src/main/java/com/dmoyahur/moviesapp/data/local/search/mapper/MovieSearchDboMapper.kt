package com.dmoyahur.moviesapp.data.local.search.mapper

import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.data.local.search.dbo.MovieSearchDbo

object MovieSearchDboMapper {

    internal fun mapToDomain(movieSearchDbo: MovieSearchDbo): MovieBo {
        return with(movieSearchDbo) {
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

    internal fun mapToDomain(moviesSearchDb: List<MovieSearchDbo>): List<MovieBo> =
        moviesSearchDb.map { mapToDomain(it) }

    internal fun mapToDatabase(movieBo: MovieBo): MovieSearchDbo {
        return with(movieBo) {
            MovieSearchDbo(
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

    internal fun String.toDatabaseQuery() = "%$this%"
}