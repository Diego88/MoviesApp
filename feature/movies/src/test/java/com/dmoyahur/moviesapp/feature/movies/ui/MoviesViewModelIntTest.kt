package com.dmoyahur.moviesapp.feature.movies.ui

import app.cash.turbine.test
import com.dmoyahur.moviesapp.data.test.movies.FakeMoviesRepository
import com.dmoyahur.moviesapp.feature.movies.domain.GetPopularMoviesUseCase
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import com.dmoyahur.moviesapp.testShared.testrules.CoroutinesTestRule
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MoviesViewModelIntTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: MoviesViewModel

    private val movies = MovieMock.movies

    @Test
    fun `when view model is created, then fetch popular movies`() =
        runTest {
            val expectedMovies = movies
            initViewModel(remoteMovies = expectedMovies)

            viewModel.state.test {
                assertEquals(MoviesUiState.Loading, awaitItem())
                assertEquals(MoviesUiState.Success(movies = expectedMovies), awaitItem())
            }
        }

    @Test
    fun `when view model is created, then return updated popular movies`() = runTest {
        val localMovies = movies.take(4)
        val expectedMovies =
            localMovies.map { it.copy(voteAverage = it.voteAverage * 2) } + movies.subList(
                5,
                movies.size
            )
        initViewModel(remoteMovies = expectedMovies, localMovies = localMovies)

        viewModel.state.test {
            assertEquals(MoviesUiState.Loading, awaitItem())
            assertEquals(MoviesUiState.Success(movies = expectedMovies), awaitItem())
        }
    }

    @Test
    fun `when local movies are not empty and fetch movies fails, then return local movies`() =
        runTest {
            val expectedMovies = movies
            initViewModel(remoteMovies = null, localMovies = expectedMovies)

            viewModel.state.test {
                assertEquals(MoviesUiState.Loading, awaitItem())
                assertEquals(MoviesUiState.Success(movies = expectedMovies), awaitItem())
            }
        }

    @Test
    fun `when local movies are empty and fetch movies fails, then get error state`() {
        val expectedException = AsyncException.ConnectionError("Connection error")

        try {
            runBlocking { initViewModel(remoteMovies = null) }
        } catch (e: Exception) {
            assertEquals(expectedException, e)
        }
    }

    private fun initViewModel(
        remoteMovies: List<MovieBo>? = emptyList(),
        localMovies: List<MovieBo> = emptyList(),
    ) {
        val moviesRepository = FakeMoviesRepository(remoteMovies, localMovies)
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(moviesRepository)
        viewModel = MoviesViewModel(getPopularMoviesUseCase)
    }
}