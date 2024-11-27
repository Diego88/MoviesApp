package com.dmoyahur.moviesapp.data.local.movies.mapper

import com.dmoyahur.moviesapp.data.local.movies.dbo.MovieDbo
import com.dmoyahur.moviesapp.model.MovieBo

object MovieDboMapper {

    internal fun mapToDomain(movieDbo: MovieDbo): MovieBo {
        return with(movieDbo) {
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

    internal fun mapToDomain(moviesDb: List<MovieDbo>): List<MovieBo> =
        moviesDb.map { mapToDomain(it) }

    private fun mapToDatabase(movieBo: MovieBo): MovieDbo {
        return with(movieBo) {
            MovieDbo(
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

    internal fun mapToDatabase(moviesBo: List<MovieBo>): List<MovieDbo> =
        moviesBo.map { mapToDatabase(it) }
}