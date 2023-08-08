package com.example.network

import com.example.network.movies.model.MoviesListResponse
import io.reactivex.Single

interface INetworkDataSource {
    fun getMovies(pageNumber: Int): Single<MoviesListResponse>
}