package com.dmoyahur.moviesapp.ui.movies

import com.dmoyahur.moviesapp.domain.MovieBo

data class MoviesUiState(
    val movies: List<MovieBo> = emptyList(),
    val error: Throwable? = null,
    val loading: Boolean = false
)