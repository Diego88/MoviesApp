package com.dmoyahur.moviesapp.feature.detail.ui

import com.dmoyahur.moviesapp.model.MovieBo

sealed interface DetailUiState {

    data object Loading : DetailUiState

    data class Error(val exception: Throwable) : DetailUiState

    data class Success(val movie: MovieBo) : DetailUiState
}