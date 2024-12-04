package com.dmoyahur.moviesapp.feature.search.domain

import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import com.dmoyahur.moviesapp.model.MovieBo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreviousSearchesUseCase @Inject constructor(private val repository: SearchRepository) {

    operator fun invoke(): Flow<List<MovieBo>> = repository.previousSearches
}