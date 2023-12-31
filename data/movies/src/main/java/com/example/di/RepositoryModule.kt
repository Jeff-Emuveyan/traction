package com.example.di

import androidx.paging.Pager
import com.example.common.entities.MovieEntity
import com.example.movies.IMoviesRepository
import com.example.movies.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import javax.inject.Inject

class RepositoryModuleImpl @Inject constructor (private val repository: MoviesRepository):
    IMoviesRepository {

    override fun getMovies(): Pager<Int, MovieEntity> {
        return repository.getMovies()
    }

    override fun get(movieTitle: String): Single<MovieEntity?> {
        return repository.get(movieTitle)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindApi(impl: RepositoryModuleImpl): IMoviesRepository
}