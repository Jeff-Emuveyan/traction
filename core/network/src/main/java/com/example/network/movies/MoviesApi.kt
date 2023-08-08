package com.example.network.movies
import com.example.network.movies.model.MoviesListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val API_KEY =
    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NzQxOTc5MGZjYmYxOGE5YmY3MjJiNzM" +
    "zMzEwMDM2YiIsInN1YiI6IjY0Y2FkOWIxNGZkMTQxMDE0NmI3MDJiMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZX" +
    "JzaW9uIjoxfQ.2h5P2ZjF2-ajA8UasvavLSmna7-HMfEisM_C7VP3ZDI"
private const val LANGUAGE = "en-US"

interface MoviesApi {

    @Headers(
        "accept: application/json",
        "Authorization: $API_KEY"
    )
    @GET("now_playing")
    fun getMovies(@Query("language") language: String = LANGUAGE,
                  @Query("page") pageNumber: Int): Single<MoviesListResponse>
}