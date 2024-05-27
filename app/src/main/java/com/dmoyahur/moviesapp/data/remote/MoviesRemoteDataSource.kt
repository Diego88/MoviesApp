package com.dmoyahur.moviesapp.data.remote

import com.dmoyahur.moviesapp.domain.MovieBo

private const val POSTER_URL = "https://image.tmdb.org/t/p/"

class MoviesRemoteDataSource {

    suspend fun fetchPopularMovies(): List<MovieBo> {
        return MoviesClient.instance.fetchPopularMovies()
            .results
            .map { it.toBo() }
    }
}

private fun MovieDto.toBo() = MovieBo(
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