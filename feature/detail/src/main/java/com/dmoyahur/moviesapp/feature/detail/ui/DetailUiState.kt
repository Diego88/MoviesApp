package com.dmoyahur.moviesapp.feature.detail.ui

import com.dmoyahur.core.model.MovieBo

data class DetailUiState(
    val movie: MovieBo? = null,
    val loading: Boolean = false,
    val error: Throwable? = null
)