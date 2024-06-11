package com.dmoyahur.moviesapp.feature.detail.domain

import com.dmoyahur.moviesapp.data.repository.movies.MoviesRepository
import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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

class GetMovieByIdUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var moviesRepository: MoviesRepository

    @MockK
    lateinit var searchRepository: SearchRepository

    private lateinit var getMovieByIdUseCase: GetMovieByIdUseCase

    @Before
    fun setUp() {
        getMovieByIdUseCase = GetMovieByIdUseCase(moviesRepository, searchRepository)
    }

    @Test
    fun `when invoke is called and fromSearch is false, then return movie from movies repository`() {
        val expectedMovie = MovieMock.movies.first()
        every { moviesRepository.findMovieById(any()) } returns flowOf(expectedMovie)

        val movie = runBlocking { getMovieByIdUseCase(1, false).first() }

        coVerify(exactly = 0) { searchRepository.findMovieSearchById(1) }
        coVerify { moviesRepository.findMovieById(1) }
        assertEquals(expectedMovie, movie)
    }

    @Test
    fun `when invoke is called and fromSearch is true, then return movie from search repository`() {
        val expectedMovie = MovieMock.movies.first()
        every { searchRepository.findMovieSearchById(any()) } returns flowOf(expectedMovie)

        val movie = runBlocking { getMovieByIdUseCase(1, true).first() }

        coVerify { searchRepository.findMovieSearchById(1) }
        coVerify(exactly = 0) { moviesRepository.findMovieById(1) }
        assertEquals(expectedMovie, movie)
    }

    @Test
    fun `when invoke fails, then throw exception`() {
        val expectedException =
            AsyncException.UnknownError("Unknown error", IllegalStateException("Unknown error"))
        coEvery { searchRepository.findMovieSearchById(any()) } throws expectedException

        assertThrows(expectedException.debugMessage, expectedException::class.java) {
            runBlocking { getMovieByIdUseCase(1, true) }
        }
    }
}