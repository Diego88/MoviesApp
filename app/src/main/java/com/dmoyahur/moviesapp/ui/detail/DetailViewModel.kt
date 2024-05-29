package com.dmoyahur.moviesapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmoyahur.moviesapp.data.MoviesRepository
import com.dmoyahur.moviesapp.ui.common.Result
import com.dmoyahur.moviesapp.ui.common.asResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(repository: MoviesRepository, id: Int) : ViewModel() {

    val state: StateFlow<DetailUiState> = repository.findMovieById(id)
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