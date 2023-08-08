package com.example.movies.model

data class Movie(val id: Int,
                 val title: String,
                 val releaseDate: String,
                 val posterUrl: String,
                 val overview: String
                 )