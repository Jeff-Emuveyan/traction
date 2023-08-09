package com.example.common.model

data class Movie(val title: String,
                 val releaseDate: String,
                 val posterUrl: String,
                 val overview: String,
                 val backdropPath: String?,
                 val language: String?,
                 val popularity: Double?,
                 val voteAverage: Double?,
                 val voteCount: Int?)