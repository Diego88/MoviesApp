package com.dmoyahur.moviesapp.testShared

import com.dmoyahur.moviesapp.model.MovieBo
import kotlin.random.Random

object MovieMock {

    val movies = (1..10).map {
        MovieBo(
            id = it,
            title = "Movie $it",
            overview = "Overview $it",
            popularity = Random.nextDouble(0.0, 10_000.0),
            releaseDate = "",
            poster = null,
            backdrop = null,
            originalTitle = "Movie $it",
            originalLanguage = "en",
            voteAverage = it / 10.0
        )
    }
}