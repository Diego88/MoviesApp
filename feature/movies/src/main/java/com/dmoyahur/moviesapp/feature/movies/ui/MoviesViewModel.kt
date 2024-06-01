package com.dmoyahur.moviesapp.feature.movies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmoyahur.moviesapp.core.ui.Result
import com.dmoyahur.moviesapp.core.ui.asResult
import com.dmoyahur.moviesapp.domain.movies.usecases.FetchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(fetchMoviesUseCase: FetchMoviesUseCase) : ViewModel() {

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