package com.dmoyahur.moviesapp.ui.search

import com.dmoyahur.moviesapp.domain.model.MovieBo

data class SearchUiState(
    val query: String = "",
    val movies: List<MovieBo> = emptyList(),
    val previousSearches: List<MovieBo> = emptyList(),
    val error: Throwable? = null,
    val loading: Boolean = false
)