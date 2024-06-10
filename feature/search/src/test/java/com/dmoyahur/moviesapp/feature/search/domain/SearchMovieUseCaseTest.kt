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
        val expectedMovies = listOf(MovieMock.movies.first(), MovieMock.movies.last())
        coEvery { repository.searchMovie(any()) } returns flowOf(expectedMovies)

        val movies = runBlocking { searchMovieUseCase("Movie 1").first() }

        assertEquals(expectedMovies, movies)
    }

    @Test
    fun `when invoke fails, then throws exception`() {
        val expectedException = AsyncException.ConnectionError("Connection error")
        coEvery { repository.searchMovie(any()) } throws expectedException

        var exception: Exception? = null
        try {
            runBlocking { searchMovieUseCase("Movie") }
        } catch (e: Exception) {
            exception = e
        }

        assertEquals(expectedException, exception)
    }
}