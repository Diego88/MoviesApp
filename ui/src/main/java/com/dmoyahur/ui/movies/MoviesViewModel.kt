package com.dmoyahur.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmoyahur.domain.usecases.FetchMoviesUseCase
import com.dmoyahur.ui.common.Result
import com.dmoyahur.ui.common.asResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MoviesViewModel(fetchMoviesUseCase: FetchMoviesUseCase) : ViewModel() {

    val state: StateFlow<MoviesUiState> = fetchMoviesUseCase()
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> MoviesUiState(movies = result.data)
                is Result.Error -> MoviesUiState(error = result.exception)
                else -> MoviesUiState(loading = true)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MoviesUiState(loading = true)
        )
}