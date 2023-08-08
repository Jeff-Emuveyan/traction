package com.example.movies.ui

import androidx.lifecycle.ViewModel
import com.example.movies.IMoviesRepository
import com.example.movies.model.MoviesScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(private val repository: IMoviesRepository): ViewModel() {

    private val _uiState = MutableStateFlow<MoviesScreenUiState>(MoviesScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getMovies() = repository.getMovies()
}