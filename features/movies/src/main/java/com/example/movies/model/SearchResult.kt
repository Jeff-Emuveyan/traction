package com.example.movies.model

import com.example.common.model.Movie

sealed class SearchResult(val movieSearchTitle: String) {
    class NoResult(val searchTitle: String) : SearchResult(searchTitle)
    class Result(val movie: Movie): SearchResult(movie.title)
}