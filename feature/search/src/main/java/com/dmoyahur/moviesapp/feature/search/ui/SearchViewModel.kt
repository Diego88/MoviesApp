package com.dmoyahur.moviesapp.feature.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmoyahur.core.model.MovieBo
import com.dmoyahur.moviesapp.core.ui.Result
import com.dmoyahur.moviesapp.core.ui.asResult
import com.dmoyahur.moviesapp.domain.search.usecases.GetPreviousSearchesUseCase
import com.dmoyahur.moviesapp.domain.search.usecases.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    getPreviousSearchesUseCase: GetPreviousSearchesUseCase
) : ViewModel() {

    private val query = MutableStateFlow("")

    private val previousSearchesState: Flow<Result<List<MovieBo>>> =
        getPreviousSearchesUseCase().asResult()

    private val moviesState: Flow<Result<List<MovieBo>>> = query
        .flatMapLatest {
            if (it.isEmpty()) flowOf(emptyList()) else searchMovieUseCase(it)
        }
        .asResult()

    val searchUiState: StateFlow<SearchUiState> =
        combine(
            query,
            previousSearchesState,
            moviesState
        ) { query, previousSearches, movies ->
            SearchUiState(
                query = query,
                previousSearches = if (previousSearches is Result.Success) previousSearches.data else emptyList(),
                movies = if (movies is Result.Success) movies.data else emptyList(),
                loading = previousSearches is Result.Loading,
                error = when {
                    previousSearches is Result.Error -> previousSearches.exception
                    movies is Result.Error -> movies.exception
                    else -> null
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchUiState(loading = true)
        )

    fun onQueryChange(query: String) {
        this.query.value = query
    }
}