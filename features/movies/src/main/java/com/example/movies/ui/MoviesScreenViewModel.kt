package com.example.movies.ui

import androidx.lifecycle.ViewModel
import com.domain.movies.IGetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(private val getMoviesUseCase: IGetMoviesUseCase): ViewModel() {
    fun getMovies() = getMoviesUseCase()
}