package com.dmoyahur.moviesapp.data.remote.search

import com.dmoyahur.moviesapp.data.remote.movies.dto.MovieDto
import com.dmoyahur.moviesapp.data.remote.movies.dto.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchNetworkApi {

    @GET("movie/{id}")
    suspend fun fetchMovieById(@Path("id") id: Int): MovieDto

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): MoviesResponseDto
}