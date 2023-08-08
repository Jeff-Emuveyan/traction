package com.example.network.movies
import android.util.Log
import com.example.network.movies.model.MoviesListResponse
import io.reactivex.Single
import javax.inject.Inject

class MoviesNetworkDataSource @Inject constructor(private val moviesApi: MoviesApi): IMoviesNetworkDataSource {

    override fun getMovies(pageNumber: Int): Single<MoviesListResponse> {
        Log.e("JEFF", "Request for page $pageNumber")
        return moviesApi.getMovies(pageNumber = pageNumber)
    }
}