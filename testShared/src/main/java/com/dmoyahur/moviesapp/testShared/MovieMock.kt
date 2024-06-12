package com.dmoyahur.moviesapp.testShared

import com.dmoyahur.moviesapp.model.MovieBo

object MovieMock {

    val movies = (1..10).map { buildMovie(it) }

    fun getMoviesBySize(size: Int) = (1..size).map { buildMovie(it) }

    private fun buildMovie(index: Int) = MovieBo(
        id = index,
        title = "Movie $index",
        overview = "overview $index",
        popularity = index.toDouble(),
        releaseDate = "2024-06-${if (index < 10) "0$index" else index}",
        poster = "https://picsum.photos/id/$index/200/300",
        backdrop = "https://picsum.photos/id/$index/400/400",
        originalTitle = "originalTitle $index",
        originalLanguage = "originalLanguage $index",
        voteAverage = index.toDouble(),
    )
}