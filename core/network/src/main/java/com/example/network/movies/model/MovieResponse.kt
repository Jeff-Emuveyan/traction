package com.example.network.movies.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Int?,
    @SerializedName("original_title")
    val title: String?,
    val overview: String?,
    @SerializedName("poster_path")
    val posterUrl: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("original_language")
    val language: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)