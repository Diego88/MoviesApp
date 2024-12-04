package com.dmoyahur.moviesapp.data.remote.movies.mapper

import com.dmoyahur.moviesapp.data.remote.movies.dto.MovieDto
import com.dmoyahur.moviesapp.model.MovieBo

object MovieDtoMapper {

    private const val POSTER_URL = "https://image.tmdb.org/t/p/"

    fun mapToDomain(movieDto: MovieDto): MovieBo {
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