package com.dmoyahur.moviesapp.feature.search.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dmoyahur.moviesapp.data.test.search.FakeSearchRepository
import com.dmoyahur.moviesapp.feature.search.domain.DeleteMovieSearchUseCase
import com.dmoyahur.moviesapp.feature.search.domain.GetPreviousSearchesUseCase
import com.dmoyahur.moviesapp.feature.search.domain.SearchMovieUseCase
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import com.dmoyahur.moviesapp.testShared.testrules.CoroutinesTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class SearchViewModelIntTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: SearchViewModel

    private val movies = MovieMock.movies

    @Test
    fun `when view model is created, then call get previous searches use case`() = runTest {
        val expectedMovies = movies
        initViewModel(localMovies = expectedMovies)

        viewModel.previousSearchesUiState.test {
            assertEquals(PreviousSearchesUiState.Loading, awaitItem())
            assertEquals(PreviousSearchesUiState.Success(expectedMovies), awaitItem())
        }
    }

    @Test
    fun `when delete a previous search, then call delete movie search use case`() = runTest {
        val movieToDelete = movies.first()
        val expectedMovies = movies - movieToDelete
        initViewModel(localMovies = movies)

        viewModel.onMovieDelete(movieToDelete)

        viewModel.previousSearchesUiState.test {
            assertEquals(PreviousSearchesUiState.Loading, awaitItem())
            assertEquals(PreviousSearchesUiState.Success(expectedMovies), awaitItem())
        }
    }

    @Test
    fun `when a previous search is deleted, then delete it from movie searches`() = runTest {
        val movieToDelete = movies.first()
        initViewModel(localMovies = movies)

        viewModel.onMovieDelete(movieToDelete)

        viewModel.previousSearchesUiState.test {
            skipItems(1)
            assertEquals(
                PreviousSearchesUiState.Success(movies - movieToDelete),
                awaitItem()
            )
        }
    }

    @Test
    fun `when type a query, then call search movie use case`() = runTest {
        val query = "Movie 1"
        val expectedMovies = listOf(movies.first(), movies.last())  // Movie 1, Movie 10
        initViewModel(remoteMovies = movies)

        viewModel.onActiveChange(true)
        viewModel.onQueryChange(query)

        viewModel.searchResultUiState.test {
            skipItems(1)    // Loading is not shown in this state
            assertEquals(SearchResultUiState.Success(expectedMovies), awaitItem())
        }
    }

    @Test
    fun `when query is empty, then show empty query state`() = runTest {
        val query = ""
        initViewModel(remoteMovies = movies)

        viewModel.onActiveChange(true)
        viewModel.onQueryChange(query)

        viewModel.searchResultUiState.test {
            skipItems(1)
            assertEquals(SearchResultUiState.EmptyQuery, awaitItem())
        }
    }

    @Test
    fun `when search movie has no results, then return empty list`() = runTest {
        val query = "Movii"
        initViewModel(remoteMovies = movies)

        viewModel.onActiveChange(true)
        viewModel.onQueryChange(query)

        viewModel.searchResultUiState.test {
            skipItems(1)
            assertEquals(SearchResultUiState.Success(emptyList()), awaitItem())
        }
    }

    @Test
    fun `when search movie fails, then get error state`() = runTest {
        val expectedException = AsyncException.ConnectionError("Connection error")
        initViewModel(remoteMovies = null)

        viewModel.onActiveChange(true)
        viewModel.onQueryChange("Movie")

        viewModel.searchResultUiState.test {
            skipItems(1)
            assertEquals(SearchResultUiState.Error(expectedException), awaitItem())
        }
    }

    private fun initViewModel(
        remoteMovies: List<MovieBo>? = emptyList(),
        localMovies: List<MovieBo> = emptyList(),
    ) {
        val repository = FakeSearchRepository(remoteMovies, localMovies)
        val searchMovieUseCase = SearchMovieUseCase(repository)
        val getPreviousSearchesUseCase = GetPreviousSearchesUseCase(repository)
        val deleteMovieSearchUseCase = DeleteMovieSearchUseCase(repository)

        viewModel = SearchViewModel(
            getPreviousSearchesUseCase,
            searchMovieUseCase,
            deleteMovieSearchUseCase,
            SavedStateHandle()
        )
    }
}