package com.dmoyahur.moviesapp.feature.search.domain

import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import com.dmoyahur.moviesapp.model.error.AsyncException
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeleteMovieSearchUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: SearchRepository

    private lateinit var deleteMovieSearchUseCase: DeleteMovieSearchUseCase

    @Before
    fun setUp() {
        deleteMovieSearchUseCase = DeleteMovieSearchUseCase(repository)
    }

    @Test
    fun `when invoke is called, then delete movie search from search repository`() {
        coJustRun { repository.deleteMovieSearch(any()) }

        runBlocking { deleteMovieSearchUseCase(1) }

        coVerify { repository.deleteMovieSearch(1) }
    }

    @Test
    fun `when invoke fails, then throw exception`() {
        val expectedException =
            AsyncException.UnknownError("Unknown error", IllegalStateException("Unknown error"))
        coEvery { repository.deleteMovieSearch(any()) } throws expectedException

        assertThrows(expectedException.debugMessage, expectedException::class.java) {
            runBlocking { deleteMovieSearchUseCase(1) }
        }
    }
}