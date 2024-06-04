package com.dmoyahur.moviesapp.feature.search.ui

import com.dmoyahur.moviesapp.core.model.MovieBo

data class PreviousSearchesState(
    val previousSearches: List<MovieBo> = emptyList(),
    val error: Throwable? = null,
    val loading: Boolean = false
)