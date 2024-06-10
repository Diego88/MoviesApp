package com.dmoyahur.moviesapp.feature.search.ui

import com.dmoyahur.moviesapp.model.MovieBo

sealed interface PreviousSearchesUiState {
    data object Loading : PreviousSearchesUiState

    data class Error(val exception: Throwable) : PreviousSearchesUiState

    data class Success(
        val previousSearches: List<MovieBo> = emptyList()
    ) : PreviousSearchesUiState {
        fun isEmpty(): Boolean = previousSearches.isEmpty()
    }
}