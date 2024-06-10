package com.dmoyahur.moviesapp.feature.movies.ui

import com.dmoyahur.moviesapp.model.MovieBo

sealed interface MoviesUiState {

    data object Loading : MoviesUiState

    data class Error(val exception: Throwable) : MoviesUiState

    data class Success(val movies: List<MovieBo> = emptyList()) : MoviesUiState
}