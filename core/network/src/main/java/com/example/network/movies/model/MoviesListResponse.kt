package com.example.network.movies.model

data class MoviesListResponse(
    val page: Int,
    val results: List<MovieResponse>
)