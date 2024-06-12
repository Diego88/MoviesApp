package com.dmoyahur.moviesapp.data.remote.movies.mapper

import com.dmoyahur.moviesapp.data.remote.MovieDtoMock
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDtoMapperTest {

    companion object {
        private const val POSTER_URL = "https://image.tmdb.org/t/p/"
    }

    @Test
    fun `given a list of remote movies, then map it correctly to a list of domain movies`() {
        val moviesDto = MovieDtoMock.moviesDto

        val moviesBo = moviesDto.map { MovieDtoMapper.mapToDomain(it) }

        moviesBo.forEachIndexed { i, movieBo ->
            with(movieBo) {
                assertEquals(id, moviesDto[i].id)
                assertEquals(title, moviesDto[i].title)
                assertEquals(overview, moviesDto[i].overview)
                assertEquals(popularity, moviesDto[i].popularity, .0)
                assertEquals(releaseDate, moviesDto[i].releaseDate)
                assertEquals(poster, "${POSTER_URL}w185${moviesDto[i].posterPath}")
                assertEquals(backdrop, "${POSTER_URL}w780${moviesDto[i].backdropPath}")
                assertEquals(originalTitle, moviesDto[i].originalTitle)
                assertEquals(originalLanguage, moviesDto[i].originalLanguage)
                assertEquals(voteAverage, moviesDto[i].voteAverage, .0)
            }
        }
    }
}