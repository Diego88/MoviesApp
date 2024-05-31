package com.dmoyahur.moviesapp.feature.movies.ui

import com.dmoyahur.core.model.MovieBo

data class MoviesUiState(
    val movies: List<MovieBo> = emptyList(),
    val error: Throwable? = null,
    val loading: Boolean = false
)