package com.dmoyahur.moviesapp.feature.detail.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dmoyahur.moviesapp.feature.detail.domain.GetMovieByIdUseCase
import com.dmoyahur.moviesapp.testShared.MovieMock
import com.dmoyahur.moviesapp.testShared.testrules.CoroutinesTestRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `when view model is created, then call get movie by id`() = runTest {
        val expectedMovie = MovieMock.movies.first()
        val getMovieByIdUseCase: GetMovieByIdUseCase = mockk()
        val savedStateHandle: SavedStateHandle = mockk {
            every {
                getStateFlow<Int?>(DetailViewModel.MOVIE_ID_ARG, null)
            } returns MutableStateFlow(1)
            every {
                getStateFlow<Boolean?>(DetailViewModel.FROM_SEARCH_ARG, null)
            } returns MutableStateFlow(false)
        }
        every { getMovieByIdUseCase(any(), any()) } returns flowOf(expectedMovie)
        val viewModel = DetailViewModel(savedStateHandle, getMovieByIdUseCase)

        viewModel.state.test {
            assertEquals(DetailUiState(loading = true), awaitItem())
            assertEquals(DetailUiState(movie = expectedMovie), awaitItem())
        }
    }
}