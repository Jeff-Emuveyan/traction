package com.example.movies.model

sealed class MoviesScreenUiState {
    object Loading : MoviesScreenUiState()
    object NetworkError: MoviesScreenUiState()
    class Success(val list: List<Movie>): MoviesScreenUiState()
}
