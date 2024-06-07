package com.dmoyahur.moviesapp.feature.search.ui

import com.dmoyahur.moviesapp.model.MovieBo

data class MoviesState(
    val movies: List<MovieBo> = emptyList(),
    val error: Throwable? = null,
    val loading: Boolean = false
)