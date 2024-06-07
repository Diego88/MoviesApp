package com.dmoyahur.moviesapp.feature.movies.ui

import com.dmoyahur.moviesapp.model.MovieBo

data class MoviesUiState(
    val movies: List<MovieBo> = emptyList(),
    val error: Throwable? = null,
    val loading: Boolean = false
)