package com.dmoyahur.moviesapp.feature.search.domain

import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import javax.inject.Inject

class DeleteMovieSearchUseCase @Inject constructor(private val repository: SearchRepository) {

    suspend operator fun invoke(id: Int) = repository.deleteMovieSearch(id)
}