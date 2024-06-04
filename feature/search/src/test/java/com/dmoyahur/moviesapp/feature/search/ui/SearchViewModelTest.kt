package com.dmoyahur.moviesapp.feature.search.ui

import app.cash.turbine.test
import com.dmoyahur.moviesapp.core.testing.MovieMock
import com.dmoyahur.moviesapp.core.testing.testrules.CoroutinesTestRule
import com.dmoyahur.moviesapp.domain.search.usecases.GetPreviousSearchesUseCase
import com.dmoyahur.moviesapp.domain.search.usecases.SearchMovieUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `when view model is created, then call get previous searches use case`() = runTest {
        val expectedMovies = MovieMock.movies
        val searchMovieUseCase: SearchMovieUseCase = mockk()
        val getPreviousSearchesUseCase: GetPreviousSearchesUseCase = mockk()
        coEvery { getPreviousSearchesUseCase() } returns flow {
            emit(expectedMovies)
        }
        val viewModel = SearchViewModel(searchMovieUseCase, getPreviousSearchesUseCase)

        viewModel.searchUiState.test {
            Assert.assertEquals(SearchUiState(loading = true), awaitItem())
            Assert.assertEquals(SearchUiState(previousSearches = expectedMovies), awaitItem())
        }
    }
}