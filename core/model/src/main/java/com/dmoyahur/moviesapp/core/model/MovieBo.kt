package com.dmoyahur.moviesapp.core.model

data class MovieBo(
    val id: Int,
    val title: String,
    val overview: String,
    val popularity: Double,
    val releaseDate: String,
    val poster: String?,
    val backdrop: String?,
    val originalTitle: String,
    val originalLanguage: String,
    val voteAverage: Double
)