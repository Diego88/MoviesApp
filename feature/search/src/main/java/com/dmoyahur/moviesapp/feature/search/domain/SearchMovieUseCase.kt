package com.dmoyahur.moviesapp.feature.search.domain

import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val repository: SearchRepository) {

    suspend operator fun invoke(query: String): Flow<List<MovieBo>> = repository.searchMovie(query)
}