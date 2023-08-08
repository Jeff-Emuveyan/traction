package com.example.network
import com.example.network.movies.MoviesApi
import com.example.network.movies.model.MoviesListResponse
import io.reactivex.Single
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val moviesApi: MoviesApi): INetworkDataSource {

    override fun getMovies(pageNumber: Int): Single<MoviesListResponse> {
        return moviesApi.getMovies(pageNumber = pageNumber)
    }
}