package com.dmoyahur.moviesapp.feature.search.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmoyahur.moviesapp.common.ui.model.Result
import com.dmoyahur.moviesapp.common.ui.model.asResult
import com.dmoyahur.moviesapp.feature.search.domain.DeleteMovieSearchUseCase
import com.dmoyahur.moviesapp.feature.search.domain.GetPreviousSearchesUseCase
import com.dmoyahur.moviesapp.feature.search.domain.SearchMovieUseCase
import com.dmoyahur.moviesapp.model.MovieBo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    getPreviousSearchesUseCase: GetPreviousSearchesUseCase,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val deleteMovieSearchUseCase: DeleteMovieSearchUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val SEARCH_QUERY = "searchQuery"
        private const val SEARCH_ACTIVE = "searchActive"
    }

    val query = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")
    val active = savedStateHandle.getStateFlow(key = SEARCH_ACTIVE, initialValue = false)

    val previousSearchesUiState: StateFlow<PreviousSearchesUiState> =
        getPreviousSearchesUseCase()
            .asResult()
            .map { result ->
                when (result) {
                    is Result.Success -> PreviousSearchesUiState.Success(result.data)
                    is Result.Error -> PreviousSearchesUiState.Error(result.exception)
                    is Result.Loading -> PreviousSearchesUiState.Loading
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PreviousSearchesUiState.Loading
            )

    val searchResultUiState: StateFlow<SearchResultUiState> =
        query.flatMapLatest {
            if (it.isEmpty()) {
                flowOf(SearchResultUiState.EmptyQuery)
            } else {
                searchMovieUseCase(it)
                    .map<List<MovieBo>, SearchResultUiState> { movies ->
                        SearchResultUiState.Success(movies)
                    }.catch { exception ->
                        emit(SearchResultUiState.Error(exception))
                    }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchResultUiState.Loading
        )

    fun onQueryChange(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    fun onActiveChange(active: Boolean) {
        savedStateHandle[SEARCH_ACTIVE] = active
    }

    fun onMovieDelete(movie: MovieBo) {
        viewModelScope.launch {
            deleteMovieSearchUseCase(movie.id)
        }
    }
}