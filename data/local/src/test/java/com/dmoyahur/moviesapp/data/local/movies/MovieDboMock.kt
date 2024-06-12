package com.dmoyahur.moviesapp.data.local.movies

import com.dmoyahur.moviesapp.data.local.movies.dbo.MovieDbo

object MovieDboMock {

    val moviesDbo = (1..10).map { index ->
        MovieDbo(
            id = index,
            title = "Movie $index",
            overview = "overview $index",
            popularity = index.toDouble(),
            releaseDate = "2024-06-${if (index < 10) "0$index" else index}",
            poster = "https://picsum.photos/id/$index/200/300",
            backdrop = "https://picsum.photos/id/$index/400/400",
            originalTitle = "originalTitle $index",
            originalLanguage = "originalLanguage $index",
            voteAverage = index.toDouble()
        )
    }
}