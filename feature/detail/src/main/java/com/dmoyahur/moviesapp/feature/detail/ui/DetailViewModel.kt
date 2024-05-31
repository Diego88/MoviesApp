package com.dmoyahur.moviesapp.feature.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmoyahur.domain.detail.GetMovieByIdUseCase
import com.dmoyahur.moviesapp.core.ui.Result
import com.dmoyahur.moviesapp.core.ui.asResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    getMovieByIdUseCase: GetMovieByIdUseCase,
    id: Int,
    fromSearch: Boolean
) : ViewModel() {

    val state: StateFlow<DetailUiState> = getMovieByIdUseCase(id, fromSearch)
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> DetailUiState(movie = result.data)
                is Result.Error -> DetailUiState(error = result.exception)
                else -> DetailUiState(loading = true)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState(loading = true)
        )
}