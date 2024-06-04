package com.dmoyahur.moviesapp.feature.movies.ui

import app.cash.turbine.test
import com.dmoyahur.moviesapp.core.testing.MovieMock
import com.dmoyahur.moviesapp.core.testing.testrules.CoroutinesTestRule
import com.dmoyahur.moviesapp.domain.movies.usecases.GetPopularMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
            assertEquals(MoviesUiState(loading = true), awaitItem())
            assertEquals(MoviesUiState(movies = expectedMovies), awaitItem())
        }
    }
}