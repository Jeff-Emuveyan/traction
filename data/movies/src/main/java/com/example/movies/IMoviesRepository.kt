package com.example.movies

import androidx.paging.Pager
import com.example.database.entities.MovieEntity

interface IMoviesRepository {

    fun getMovies(pageNumber: Int): Pager<Int, MovieEntity>
}