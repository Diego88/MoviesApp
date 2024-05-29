package com.dmoyahur.moviesapp.ui.detail

import com.dmoyahur.moviesapp.domain.MovieBo

data class DetailUiState(
    val movie: MovieBo? = null,
    val loading: Boolean = false,
    val error: Throwable? = null
)