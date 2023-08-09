package com.domain.movies.di

import androidx.paging.PagingData
import com.domain.movies.GetMovieUseCase
import com.domain.movies.GetMoviesUseCase
import com.domain.movies.IGetMovieUseCase
import com.domain.movies.IGetMoviesUseCase
import com.example.common.entities.MovieEntity
import com.example.common.model.Movie
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUseCaseModuleImpl @Inject constructor(private val getMovieUseCase: GetMovieUseCase): IGetMovieUseCase {

    override fun invoke(movieTitle: String): Single<Movie?> {
        return getMovieUseCase.invoke(movieTitle)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class GetMovieUseCaseModule {
    @Binds
    abstract fun bindApi(impl: GetMovieUseCaseModuleImpl): IGetMovieUseCase
}