package com.dmoyahur.moviesapp.domain.search.usecases

import com.dmoyahur.moviesapp.core.model.MovieBo
import com.dmoyahur.moviesapp.domain.search.data.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val repository: SearchRepository) {

    suspend operator fun invoke(query: String): Flow<List<MovieBo>> = repository.searchMovie(query)
}