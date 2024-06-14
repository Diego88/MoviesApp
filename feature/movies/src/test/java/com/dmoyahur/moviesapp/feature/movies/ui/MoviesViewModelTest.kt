package com.dmoyahur.moviesapp.feature.movies.ui

import app.cash.turbine.test
import com.dmoyahur.moviesapp.feature.movies.domain.GetPopularMoviesUseCase
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import com.dmoyahur.moviesapp.testShared.testrules.CoroutinesTestRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test

class MoviesViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `when view model is created, then call get popular movies`() = runTest {
        val expectedMovies = MovieMock.movies
        val getPopularMoviesUseCase: GetPopularMoviesUseCase = mockk()
        every { getPopularMoviesUseCase() } returns flowOf(expectedMovies)
        val viewModel = MoviesViewModel(getPopularMoviesUseCase)

        viewModel.state.test {
            assertEquals(MoviesUiState.Loading, awaitItem())
            assertEquals(MoviesUiState.Success(movies = expectedMovies), awaitItem())
        }
    }

    @Test
    fun `when get popular movies fails, then throw exception`() {
        val expectedException = AsyncException.ConnectionError("Connection error")
        val getPopularMoviesUseCase: GetPopularMoviesUseCase = mockk()
        every { getPopularMoviesUseCase() } throws expectedException

        assertThrows("Connection error", AsyncException.ConnectionError::class.java) {
            runBlocking { MoviesViewModel(getPopularMoviesUseCase) }
        }
    }
}