package com.dmoyahur.moviesapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmoyahur.moviesapp.data.MoviesRepository
import com.dmoyahur.moviesapp.domain.MovieBo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MoviesViewModel(repository: MoviesRepository) : ViewModel() {

    val state: StateFlow<UiState> = repository.movies
        .map { UiState(movies = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )

    data class UiState(
        val loading: Boolean = false,
        val movies: List<MovieBo> = emptyList()
    )
}