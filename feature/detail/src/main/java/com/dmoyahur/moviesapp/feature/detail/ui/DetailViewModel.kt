package com.dmoyahur.moviesapp.feature.detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmoyahur.moviesapp.common.ui.model.Result
import com.dmoyahur.moviesapp.common.ui.model.asResult
import com.dmoyahur.moviesapp.feature.detail.domain.GetMovieByIdUseCase
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
    getMovieByIdUseCase: GetMovieByIdUseCase,
) : ViewModel() {

    companion object {
        const val MOVIE_ID_ARG = "movieId"
        const val FROM_SEARCH_ARG = "fromSearch"
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
                is Result.Success -> DetailUiState.Success(result.data)
                is Result.Error -> DetailUiState.Error(result.exception)
                is Result.Loading -> DetailUiState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState.Loading
        )
}