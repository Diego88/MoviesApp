package com.dmoyahur.moviesapp.domain.search.usecases

import com.dmoyahur.core.model.MovieBo
import com.dmoyahur.moviesapp.domain.search.data.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreviousSearchesUseCase @Inject constructor(private val repository: SearchRepository) {

    operator fun invoke(): Flow<List<MovieBo>> = repository.previousSearches
}