package com.dmoyahur.remote.mapper

import com.dmoyahur.domain.model.MovieBo
import com.dmoyahur.remote.model.MovieDto

object MovieDtoMapper {

    private const val POSTER_URL = "https://image.tmdb.org/t/p/"

    internal fun mapToDomain(movieDto: MovieDto): MovieBo {
        return with(movieDto) {
            MovieBo(
                id = id,
                title = title,
                overview = overview,
                popularity = popularity,
                releaseDate = releaseDate,
                poster = "${POSTER_URL}w185/$posterPath",
                backdrop = backdropPath?.let { "${POSTER_URL}w780/$it" },
                originalTitle = originalTitle,
                originalLanguage = originalLanguage,
                voteAverage = voteAverage
            )
        }
    }
}