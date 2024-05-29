package com.dmoyahur.ui.movies

import com.dmoyahur.domain.model.MovieBo

data class MoviesUiState(
    val movies: List<MovieBo> = emptyList(),
    val error: Throwable? = null,
    val loading: Boolean = false
)