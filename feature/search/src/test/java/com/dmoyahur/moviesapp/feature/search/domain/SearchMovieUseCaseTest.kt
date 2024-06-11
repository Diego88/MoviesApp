package com.dmoyahur.moviesapp.feature.search.domain

import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchMovieUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: SearchRepository

    private lateinit var searchMovieUseCase: SearchMovieUseCase

    @Before
    fun setUp() {
        searchMovieUseCase = SearchMovieUseCase(repository)
    }

    @Test
    fun `when invoke is called, then return movies from search repository`() {
        val query = "Movie 1"
        val expectedMovies = listOf(MovieMock.movies.first(), MovieMock.movies.last())
        coEvery { repository.searchMovie(query) } returns flowOf(expectedMovies)

        val movies = runBlocking { searchMovieUseCase(query).first() }

        assertEquals(expectedMovies, movies)
    }

    @Test
    fun `when invoke fails, then throw exception`() {
        val expectedException = AsyncException.ConnectionError("Connection error")
        coEvery { repository.searchMovie(any()) } throws expectedException

        assertThrows(expectedException.debugMessage, expectedException::class.java) {
            runBlocking { searchMovieUseCase("Movie") }
        }
    }
}