package com.dmoyahur.moviesapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService {

    @GET("movie/popular")
    suspend fun fetchPopularMovies(): AsyncResult

    @GET("movie/{id}")
    suspend fun fetchMovieById(@Path("id") id: Int): MovieDto
}