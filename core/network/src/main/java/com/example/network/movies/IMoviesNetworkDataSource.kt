package com.example.network.movies

import com.example.network.movies.model.MoviesListResponse
import io.reactivex.Single

interface IMoviesNetworkDataSource {
    fun getMovies(pageNumber: Int): Single<MoviesListResponse>
}