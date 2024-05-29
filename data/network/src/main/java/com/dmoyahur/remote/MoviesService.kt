package com.dmoyahur.remote

import com.dmoyahur.remote.model.AsyncResult
import com.dmoyahur.remote.model.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService {

    @GET("movie/popular")
    suspend fun fetchPopularMovies(): AsyncResult

    @GET("movie/{id}")
    suspend fun fetchMovieById(@Path("id") id: Int): MovieDto
}