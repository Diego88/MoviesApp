package com.dmoyahur.moviesapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmoyahur.moviesapp.data.MoviesRepository
import com.dmoyahur.moviesapp.ui.common.Result
import com.dmoyahur.moviesapp.ui.common.asResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MoviesViewModel(repository: MoviesRepository) : ViewModel() {

    val state: StateFlow<MoviesUiState> = repository.movies
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