package com.example.di

import androidx.paging.Pager
import com.example.database.entities.MovieEntity
import com.example.movies.IMoviesRepository
import com.example.movies.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class RepositoryModuleImpl @Inject constructor (private val repository: MoviesRepository):
    IMoviesRepository {

    override fun getMovies(pageNumber: Int): Pager<Int, MovieEntity> {
        return repository.getMovies(pageNumber)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindApi(impl: RepositoryModuleImpl): IMoviesRepository
}