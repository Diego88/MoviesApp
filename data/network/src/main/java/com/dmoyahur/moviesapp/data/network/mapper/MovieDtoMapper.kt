package com.dmoyahur.moviesapp.data.network.mapper

import com.dmoyahur.moviesapp.domain.model.MovieBo
import com.dmoyahur.moviesapp.data.network.model.MovieDto

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
                poster = posterPath?.let { "${POSTER_URL}w185$it" },
                backdrop = backdropPath?.let { "${POSTER_URL}w780$it" },
                originalTitle = originalTitle,
                originalLanguage = originalLanguage,
                voteAverage = voteAverage
            )
        }
    }
}