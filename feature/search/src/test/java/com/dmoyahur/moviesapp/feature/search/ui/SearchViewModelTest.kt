package com.dmoyahur.moviesapp.feature.search.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dmoyahur.moviesapp.feature.search.domain.GetPreviousSearchesUseCase
import com.dmoyahur.moviesapp.feature.search.domain.SearchMovieUseCase
import com.dmoyahur.moviesapp.testShared.MovieMock
import com.dmoyahur.moviesapp.testShared.testrules.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var searchMovieUseCase: SearchMovieUseCase

    @MockK
    lateinit var getPreviousSearchesUseCase: GetPreviousSearchesUseCase

    private lateinit var viewModel: SearchViewModel

    private val movies = MovieMock.movies

    @Before
    fun setUp() {
        coEvery { getPreviousSearchesUseCase() } returns flowOf(movies.take(4))
        coEvery { searchMovieUseCase(any()) } returns flowOf(movies)
    }

    @Test
    fun `when view model is created, then call get previous searches use case`() = runTest {
        val expectedMovies = movies.take(4)
        initViewModel()

        viewModel.previousSearchesUiState.test {
            assertEquals(PreviousSearchesUiState.Loading, awaitItem())
            assertEquals(PreviousSearchesUiState.Success(expectedMovies), awaitItem())
        }
    }

    @Test
    fun `when type a query, then call search movie use case`() = runTest {
        val expectedMovies = movies
        val query = "Movie"
        initViewModel()

        viewModel.onActiveChange(true)
        viewModel.onQueryChange(query)

        viewModel.searchResultUiState.test {
            assertEquals(SearchResultUiState.Loading, awaitItem())
            assertEquals(SearchResultUiState.Success(expectedMovies), awaitItem())
        }
    }

    @Test
    fun `when query is empty, then show empty query state`() = runTest {
        val query = ""
        initViewModel()

        viewModel.onActiveChange(true)
        viewModel.onQueryChange(query)

        viewModel.searchResultUiState.test {
            assertEquals(SearchResultUiState.Loading, awaitItem())
            assertEquals(SearchResultUiState.EmptyQuery, awaitItem())
        }
    }

    @Test
    fun `when search has no results, then return empty list`() = runTest {
        val query = "Movii"
        coEvery { searchMovieUseCase(any()) } returns flowOf(emptyList())
        initViewModel()

        viewModel.onActiveChange(true)
        viewModel.onQueryChange(query)

        viewModel.searchResultUiState.test {
            assertEquals(SearchResultUiState.Loading, awaitItem())
            assertEquals(SearchResultUiState.Success(emptyList()), awaitItem())
        }
    }

    private fun initViewModel(savedStateHandle: SavedStateHandle = SavedStateHandle()) {
        viewModel =
            SearchViewModel(getPreviousSearchesUseCase, searchMovieUseCase, savedStateHandle)
    }
}