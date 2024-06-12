package com.dmoyahur.moviesapp.data.remote

import com.dmoyahur.moviesapp.data.remote.movies.dto.MovieDto
import com.dmoyahur.moviesapp.data.remote.movies.dto.MoviesResponseDto

object MovieDtoMock {

    val moviesDto = (1..10).map { index ->
        MovieDto(
            id = index,
            title = "Movie $index",
            overview = "overview $index",
            popularity = index.toDouble(),
            releaseDate = "2024-06-${if (index < 10) "0$index" else index}",
            posterPath = "/$index/200/300",
            backdropPath = "/$index/400/400",
            originalTitle = "originalTitle $index",
            originalLanguage = "originalLanguage $index",
            voteAverage = index.toDouble()
        )
    }

    val moviesResponseDto = MoviesResponseDto(
        page = 1,
        results = moviesDto,
        totalPages = 10,
        totalResults = 100
    )
}