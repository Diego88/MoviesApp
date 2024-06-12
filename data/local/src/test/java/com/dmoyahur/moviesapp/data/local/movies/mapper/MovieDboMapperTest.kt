package com.dmoyahur.moviesapp.data.local.movies.mapper

import com.dmoyahur.moviesapp.data.local.movies.MovieDboMock
import com.dmoyahur.moviesapp.testShared.MovieMock
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDboMapperTest {

    @Test
    fun `given a list of database movies, then map it correctly to a list of domain movies`() {
        val moviesDbo = MovieDboMock.moviesDbo

        val moviesBo = MovieDboMapper.mapToDomain(moviesDbo)

        moviesDbo.forEachIndexed { i, movieDbo ->
            with(movieDbo) {
                assertEquals(id, moviesBo[i].id)
                assertEquals(title, moviesBo[i].title)
                assertEquals(overview, moviesBo[i].overview)
                assertEquals(popularity, moviesBo[i].popularity, .0)
                assertEquals(releaseDate, moviesBo[i].releaseDate)
                assertEquals(poster, moviesBo[i].poster)
                assertEquals(backdrop, moviesBo[i].backdrop)
                assertEquals(originalTitle, moviesBo[i].originalTitle)
                assertEquals(originalLanguage, moviesBo[i].originalLanguage)
                assertEquals(voteAverage, moviesBo[i].voteAverage, .0)
            }
        }
    }

    @Test
    fun `given a list of domain movies, then map it correctly to a list of database movies`() {
        val moviesBo = MovieMock.movies

        val moviesDbo = MovieDboMapper.mapToDatabase(moviesBo)

        moviesBo.forEachIndexed { i, movieBo ->
            with(movieBo) {
                assertEquals(id, moviesDbo[i].id)
                assertEquals(title, moviesDbo[i].title)
                assertEquals(overview, moviesDbo[i].overview)
                assertEquals(popularity, moviesDbo[i].popularity, .0)
                assertEquals(releaseDate, moviesDbo[i].releaseDate)
                assertEquals(poster, moviesDbo[i].poster)
                assertEquals(backdrop, moviesDbo[i].backdrop)
                assertEquals(originalTitle, moviesDbo[i].originalTitle)
                assertEquals(originalLanguage, moviesDbo[i].originalLanguage)
                assertEquals(voteAverage, moviesDbo[i].voteAverage, .0)
            }
        }
    }
}