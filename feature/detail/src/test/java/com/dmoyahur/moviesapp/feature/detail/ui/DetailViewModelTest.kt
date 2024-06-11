package com.dmoyahur.moviesapp.feature.detail.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dmoyahur.moviesapp.feature.detail.domain.GetMovieByIdUseCase
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import com.dmoyahur.moviesapp.testShared.testrules.CoroutinesTestRule
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var getMovieByIdUseCase: GetMovieByIdUseCase


    @MockK
    lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        every {
            savedStateHandle.getStateFlow<Int?>(DetailViewModel.MOVIE_ID_ARG, null)
        } returns MutableStateFlow(1)
        every {
            savedStateHandle.getStateFlow<Boolean?>(DetailViewModel.FROM_SEARCH_ARG, null)
        } returns MutableStateFlow(false)
    }

    @Test
    fun `when view model is created, then call get movie by id`() = runTest {
        val expectedMovie = MovieMock.movies.first()
        every { getMovieByIdUseCase(1, false) } returns flowOf(expectedMovie)

        viewModel = DetailViewModel(savedStateHandle, getMovieByIdUseCase)
        val viewModel = DetailViewModel(savedStateHandle, getMovieByIdUseCase)

        viewModel.state.test {
            assertEquals(DetailUiState.Loading, awaitItem())
            assertEquals(DetailUiState.Success(expectedMovie), awaitItem())
        }
    }

    @Test
    fun `when getMovieById fails, then return error`() = runTest {
        val expectedError =
            AsyncException.UnknownError("Unknown error", IllegalStateException("Unknown error"))
        every { getMovieByIdUseCase(any(), any()) } throws expectedError

        val viewModel = DetailViewModel(savedStateHandle, getMovieByIdUseCase)

        viewModel.state.test {
            assertEquals(DetailUiState.Loading, awaitItem())
            assertEquals(DetailUiState.Error(expectedError), awaitItem())
        }
    }
}