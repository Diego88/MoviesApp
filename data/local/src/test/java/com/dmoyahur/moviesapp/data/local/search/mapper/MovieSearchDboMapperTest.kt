package com.dmoyahur.moviesapp.data.local.search.mapper

import com.dmoyahur.moviesapp.data.local.movies.mapper.MovieDboMapper
import com.dmoyahur.moviesapp.data.local.search.MovieSearchDboMock
import com.dmoyahur.moviesapp.testShared.MovieMock
import org.junit.Assert
import org.junit.Test

class MovieSearchDboMapperTest {

    @Test
    fun `given a list of database movies search, then map it correctly to a list of domain movies`() {
        val moviesSearchDbo = MovieSearchDboMock.moviesSearchDbo

        val moviesBo = MovieSearchDboMapper.mapToDomain(moviesSearchDbo)

        moviesSearchDbo.forEachIndexed { i, movieSearchDbo ->
            with(movieSearchDbo) {
                Assert.assertEquals(id, moviesBo[i].id)
                Assert.assertEquals(title, moviesBo[i].title)
                Assert.assertEquals(overview, moviesBo[i].overview)
                Assert.assertEquals(popularity, moviesBo[i].popularity, .0)
                Assert.assertEquals(releaseDate, moviesBo[i].releaseDate)
                Assert.assertEquals(poster, moviesBo[i].poster)
                Assert.assertEquals(backdrop, moviesBo[i].backdrop)
                Assert.assertEquals(originalTitle, moviesBo[i].originalTitle)
                Assert.assertEquals(originalLanguage, moviesBo[i].originalLanguage)
                Assert.assertEquals(voteAverage, moviesBo[i].voteAverage, .0)
            }
        }
    }

    @Test
    fun `given a list of domain movies, then map it correctly to a list of database movies searches`() {
        val moviesBo = MovieMock.movies

        val moviesSearchDbo = MovieDboMapper.mapToDatabase(moviesBo)

        moviesBo.forEachIndexed { i, movieBo ->
            with(movieBo) {
                Assert.assertEquals(id, moviesSearchDbo[i].id)
                Assert.assertEquals(title, moviesSearchDbo[i].title)
                Assert.assertEquals(overview, moviesSearchDbo[i].overview)
                Assert.assertEquals(popularity, moviesSearchDbo[i].popularity, .0)
                Assert.assertEquals(releaseDate, moviesSearchDbo[i].releaseDate)
                Assert.assertEquals(poster, moviesSearchDbo[i].poster)
                Assert.assertEquals(backdrop, moviesSearchDbo[i].backdrop)
                Assert.assertEquals(originalTitle, moviesSearchDbo[i].originalTitle)
                Assert.assertEquals(originalLanguage, moviesSearchDbo[i].originalLanguage)
                Assert.assertEquals(voteAverage, moviesSearchDbo[i].voteAverage, .0)
            }
        }
    }
}
