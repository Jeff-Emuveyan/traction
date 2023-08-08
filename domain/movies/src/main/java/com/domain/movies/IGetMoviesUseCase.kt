package com.domain.movies

import androidx.paging.PagingData
import com.example.common.model.Movie
import kotlinx.coroutines.flow.Flow

interface IGetMoviesUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}