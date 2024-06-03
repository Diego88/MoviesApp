package com.dmoyahur.moviesapp.feature.detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmoyahur.domain.detail.GetMovieByIdUseCase
import com.dmoyahur.moviesapp.core.ui.model.Result
import com.dmoyahur.moviesapp.core.ui.model.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    companion object {
        private const val MOVIE_ID_ARG = "movieId"
        private const val FROM_SEARCH_ARG = "fromSearch"
    }

    private val movieId: StateFlow<Int?> =
        savedStateHandle.getStateFlow(MOVIE_ID_ARG, null)

    private val fromSearch: StateFlow<Boolean?> =
        savedStateHandle.getStateFlow(FROM_SEARCH_ARG, null)

    val state: StateFlow<DetailUiState> = combine(movieId, fromSearch) { movieId, fromSearch ->
        if (movieId != null && fromSearch != null) {
            movieId to fromSearch
        } else {
            null
        }
    }.filterNotNull()
        .flatMapLatest { (movieId, fromSearch) ->
            getMovieByIdUseCase(movieId, fromSearch)
        }
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> DetailUiState(movie = result.data)
                is Result.Error -> DetailUiState(error = result.exception)
                is Result.Loading -> DetailUiState(loading = true)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState(loading = true)
        )
}