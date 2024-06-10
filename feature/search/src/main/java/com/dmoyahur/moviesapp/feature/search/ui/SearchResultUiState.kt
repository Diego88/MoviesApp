package com.dmoyahur.moviesapp.feature.search.ui

import com.dmoyahur.moviesapp.model.MovieBo

sealed interface SearchResultUiState {
    data object Loading : SearchResultUiState

    data object EmptyQuery : SearchResultUiState

    data class Error(val exception: Throwable) : SearchResultUiState

    data class Success(
        val searchResult: List<MovieBo> = emptyList()
    ) : SearchResultUiState {
        fun isEmpty(): Boolean = searchResult.isEmpty()
    }
}