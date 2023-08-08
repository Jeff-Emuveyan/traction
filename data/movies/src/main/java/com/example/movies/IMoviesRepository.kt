package com.example.movies

import androidx.paging.Pager
import com.example.common.entities.MovieEntity

interface IMoviesRepository {

    fun getMovies(): Pager<Int, MovieEntity>
}