package com.dmoyahur.ui.detail

import com.dmoyahur.domain.model.MovieBo

data class DetailUiState(
    val movie: MovieBo? = null,
    val loading: Boolean = false,
    val error: Throwable? = null
)