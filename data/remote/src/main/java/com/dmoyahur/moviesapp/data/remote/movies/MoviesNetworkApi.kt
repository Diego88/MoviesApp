package com.dmoyahur.moviesapp.data.remote.movies

import com.dmoyahur.moviesapp.data.remote.movies.dto.MoviesResponseDto
import retrofit2.http.GET

interface MoviesNetworkApi {

    @GET("movie/popular")
    suspend fun fetchPopularMovies(): MoviesResponseDto
}