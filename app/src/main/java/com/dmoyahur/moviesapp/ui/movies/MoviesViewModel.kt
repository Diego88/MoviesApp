package com.dmoyahur.moviesapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmoyahur.moviesapp.data.MoviesRepository
import com.dmoyahur.moviesapp.domain.MovieBo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            repository.fetchPopularMovies().also { movies ->
                _state.value = UiState(
                    loading = false,
                    movies = movies
                )
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<MovieBo> = emptyList()
    )
}