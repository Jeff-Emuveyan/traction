package com.example.network.di

import com.example.network.movies.MoviesApi
import com.example.network.movies.model.MoviesListResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class MoviesApiImpl @Inject constructor(private val retrofit: Retrofit): MoviesApi {

    override fun getMovies(language: String, pageNumber: Int): Single<MoviesListResponse> {
        return retrofit.create(MoviesApi::class.java).getMovies(language, pageNumber)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesApiModule {

    @Binds
    abstract fun bindApi(apiImpl: MoviesApiImpl): MoviesApi
}