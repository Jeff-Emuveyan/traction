package com.example.movies

import androidx.paging.Pager
import com.example.common.entities.MovieEntity
import io.reactivex.Single

interface IMoviesRepository {

    fun getMovies(): Pager<Int, MovieEntity>

    fun get(movieTitle: String): Single<MovieEntity?>
}