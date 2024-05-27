package com.dmoyahur.moviesapp.data

import com.dmoyahur.moviesapp.domain.MovieBo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val POSTER_URL = "https://image.tmdb.org/t/p/w185/"

class MoviesRepository {
    suspend fun fetchPopularMovies(): List<MovieBo> = withContext(Dispatchers.IO) {
        MoviesClient.instance.fetchPopularMovies()
            .results
            .map { it.toBo() }
    }
}

private fun MovieDto.toBo() = MovieBo(
    id = id,
    title = title,
    poster = "$POSTER_URL$posterPath"
)