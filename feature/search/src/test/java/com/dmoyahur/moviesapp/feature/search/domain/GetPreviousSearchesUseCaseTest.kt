package com.dmoyahur.moviesapp.feature.search.domain

import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetPreviousSearchesUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: SearchRepository

    private lateinit var getPreviousSearchesUseCase: GetPreviousSearchesUseCase

    @Before
    fun setUp() {
        getPreviousSearchesUseCase = GetPreviousSearchesUseCase(repository)
    }

    @Test
    fun `when invoke is called, then get previous searches from search repository`() {
        val expectedMovies = MovieMock.movies
        every { repository.previousSearches } returns flowOf(expectedMovies)

        val movies = runBlocking { getPreviousSearchesUseCase().first() }

        assertEquals(expectedMovies, movies)
    }

    @Test
    fun `when invoke fails, then throws exception`() {
        val expectedException =
            AsyncException.UnknownError("Unknown error", IllegalStateException("Unknown error"))
        coEvery { repository.previousSearches } throws expectedException

        var exception: Exception? = null
        try {
            getPreviousSearchesUseCase()
        } catch (e: Exception) {
            exception = e
        }

        assertEquals(expectedException, exception)
    }
}