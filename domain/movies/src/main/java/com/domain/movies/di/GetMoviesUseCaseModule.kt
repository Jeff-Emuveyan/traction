package com.domain.movies.di

import androidx.paging.PagingData
import com.domain.movies.GetMoviesUseCase
import com.domain.movies.IGetMoviesUseCase
import com.example.common.model.Movie
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCaseModuleImpl @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase): IGetMoviesUseCase {

    override fun invoke(): Flow<PagingData<Movie>> {
        return getMoviesUseCase.invoke()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class GetMoviesUseCaseModule {
    @Binds
    abstract fun bindApi(impl: GetMoviesUseCaseModuleImpl): IGetMoviesUseCase
}