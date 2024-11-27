package com.dmoyahur.moviesapp.feature.detail.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dmoyahur.moviesapp.data.test.movies.FakeMoviesRepository
import com.dmoyahur.moviesapp.data.test.search.FakeSearchRepository
import com.dmoyahur.moviesapp.feature.detail.domain.GetMovieByIdUseCase
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import com.dmoyahur.moviesapp.testShared.testrules.CoroutinesTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DetailViewModelIntTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: DetailViewModel

    private val movies = MovieMock.movies

    @Test
    fun `when view model is created and fromSearch is false, then get movie by id`() = runTest {
        val expectedMovie = movies.first()
        initViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    DetailViewModel.MOVIE_ID_ARG to 1,
                    DetailViewModel.FROM_SEARCH_ARG to false
                )
            ),
            localMovies = movies
        )

        viewModel.state.test {
            assertEquals(DetailUiState.Loading, awaitItem())
            assertEquals(DetailUiState.Success(expectedMovie), awaitItem())
        }
    }

    @Test
    fun `when view model is created and fromSearch is true, then fetch movie by id`() = runTest {
        val expectedMovie = movies.first()
        initViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    DetailViewModel.MOVIE_ID_ARG to 1,
                    DetailViewModel.FROM_SEARCH_ARG to true
                )
            ),
            remoteMovies = movies
        )

        viewModel.state.test {
            assertEquals(DetailUiState.Loading, awaitItem())
            assertEquals(DetailUiState.Success(expectedMovie), awaitItem())
        }
    }

    @Test
    fun `when fetch movie fails and local movie is not empty, then return movie`() = runTest {
        val expectedMovie = movies.first()
        initViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    DetailViewModel.MOVIE_ID_ARG to 1,
                    DetailViewModel.FROM_SEARCH_ARG to true
                )
            ),
            remoteMovies = null,
            localMovies = movies
        )

        viewModel.state.test {
            assertEquals(DetailUiState.Loading, awaitItem())
            assertEquals(DetailUiState.Success(expectedMovie), awaitItem())
        }
    }

    @Test
    fun `when fetch movie fails and local movie is empty, then get error state`() = runTest {
        val expectedException =
            AsyncException.UnknownError("Movie not found", Throwable("Movie not found"))
        initViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    DetailViewModel.MOVIE_ID_ARG to 1,
                    DetailViewModel.FROM_SEARCH_ARG to true
                )
            ),
            remoteMovies = null,
            localMovies = emptyList()
        )

        viewModel.state.test {
            assertEquals(DetailUiState.Loading, awaitItem())
            val actual = awaitItem()
            assertTrue(actual is DetailUiState.Error)
            assertEquals(
                expectedException.message,
                (actual as DetailUiState.Error).exception.message
            )
        }
    }

    private fun initViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(),
        remoteMovies: List<MovieBo>? = emptyList(),
        localMovies: List<MovieBo> = emptyList()
    ) {
        val moviesRepository = FakeMoviesRepository(remoteMovies, localMovies)
        val searchRepository = FakeSearchRepository(remoteMovies, localMovies)
        val getMovieByIdUseCase = GetMovieByIdUseCase(moviesRepository, searchRepository)

        viewModel = DetailViewModel(savedStateHandle, getMovieByIdUseCase)
    }
}