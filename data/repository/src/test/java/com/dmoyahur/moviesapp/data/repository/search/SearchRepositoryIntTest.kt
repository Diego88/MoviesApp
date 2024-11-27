package com.dmoyahur.moviesapp.data.repository.search

import com.dmoyahur.moviesapp.data.repository.search.datasource.FakeSearchLocalDataSource
import com.dmoyahur.moviesapp.data.repository.search.datasource.FakeSearchRemoteDataSource
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class SearchRepositoryIntTest {

    companion object {
        private const val MAX_HISTORY_SIZE = 12
    }

    private lateinit var repository: SearchRepository

    private val movies = MovieMock.movies

    @Test
    fun `when previousSearches is called, then return previous searches from local data source`() {
        val expectedMovies = movies
        initRepository(localMovies = movies)

        val actual = runBlocking { repository.previousSearches.first() }

        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when findMovieSearchById is called, then return updated movie from local data source`() = runTest {
        val movie = movies.first()
        val expectedMovie = movie.copy(popularity = movie.popularity + 1)
        val remoteMovies = listOf(expectedMovie)
        initRepository(remoteMovies = remoteMovies, localMovies = movies)

        val actual = repository.findMovieSearchById(1).first()

        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when call findMovieSearchById and searches count is greater than max count, then delete oldest searches`() =
        runTest {
            val movies = MovieMock.getMoviesBySize(MAX_HISTORY_SIZE)
            val expectedMovie = movies.first().copy(id = 15)
            val remoteMovies = listOf(expectedMovie)
            initRepository(remoteMovies = remoteMovies, localMovies = movies)

            val oldestBeforeDelete = repository.previousSearches.first()[0]
            val actual = repository.findMovieSearchById(15).first()
            val oldestAfterDelete = repository.previousSearches.first()[0]

            assertEquals(movies[0], oldestBeforeDelete)
            assertEquals(expectedMovie, actual)
            assertEquals(movies[1], oldestAfterDelete)
        }

    @Test
    fun `when fetchMovieSearchById fails and local movie is not empty, then return local movie`() {
        val expectedMovie = movies.first()
        initRepository(localMovies = movies)

        val actual = runBlocking { repository.findMovieSearchById(1).first() }

        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when fetchMovieSearchById fails and local movie is null, then throw exception`() {
        val expectedException =
            AsyncException.UnknownError("Movie not found", Exception("Movie not found"))
        initRepository()

        assertThrows(expectedException.debugMessage, expectedException::class.java) {
            runBlocking { repository.findMovieSearchById(1).first() }
        }
    }

    @Test
    fun `when searchMovie is called, then return movies from remote data source`() {
        val query = "Movie 1"
        val expectedMovies = listOf(movies.first(), movies.last())
        initRepository(remoteMovies = movies)

        val actual = runBlocking { repository.searchMovie(query).first() }

        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when deleteMovieSearch is called, then delete movie search from local data source`() = runTest {
        initRepository(localMovies = movies)

        val sizeBeforeDelete = repository.previousSearches.first().size
        repository.deleteMovieSearch(1)
        val sizeAfterDelete = repository.previousSearches.first().size

        assertEquals(movies.size, sizeBeforeDelete)
        assertEquals(movies.size - 1, sizeAfterDelete)
    }

    private fun initRepository(
        remoteMovies: List<MovieBo> = emptyList(),
        localMovies: List<MovieBo> = emptyList()
    ) {
        val remoteDataSource = FakeSearchRemoteDataSource(remoteMovies)
        val localDataSource = FakeSearchLocalDataSource(localMovies)
        repository = SearchRepositoryImpl(remoteDataSource, localDataSource)
    }
}