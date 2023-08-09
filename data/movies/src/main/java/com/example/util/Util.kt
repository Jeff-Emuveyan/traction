package com.example.util

import com.example.common.entities.MovieEntity
import com.example.network.movies.model.MovieResponse

fun MovieResponse.toEntity(pageNumber: Int): MovieEntity {
    return MovieEntity(
        title,
        overview,
        posterUrl,
        releaseDate,
        backdropPath,
        language,
        popularity,
        voteAverage,
        voteCount,
        pageNumber = pageNumber)
}

fun List<MovieResponse>.toEntity(pageNumber: Int): List<MovieEntity> {
    val result = mutableListOf<MovieEntity>()
    forEach { result.add(it.toEntity(pageNumber)) }
    return result
}