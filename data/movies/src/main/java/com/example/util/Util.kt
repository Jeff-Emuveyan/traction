package com.example.util

import com.example.database.entities.MovieEntity
import com.example.network.movies.model.MovieResponse

fun MovieResponse.toEntity(pageNumber: Int): MovieEntity {
    return MovieEntity(title, overview, posterUrl, releaseDate, pageNumber)
}

fun List<MovieResponse>.toEntity(pageNumber: Int): List<MovieEntity> {
    val result = mutableListOf<MovieEntity>()
    forEach { result.add(it.toEntity(pageNumber)) }
    return result
}