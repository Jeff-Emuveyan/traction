package com.example.movies.model

import com.example.common.model.Movie

sealed class SearchResult {
    object NoResult : SearchResult()
    class Result(val movie: Movie): SearchResult()
}