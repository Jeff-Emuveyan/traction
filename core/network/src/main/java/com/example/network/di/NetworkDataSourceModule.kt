package com.example.network.di

import com.example.network.movies.IMoviesNetworkDataSource
import com.example.network.movies.MoviesNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This module is where the dependency for all network data source (not just IMovieNetworkDataSource) will be created.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {
    @Binds
    abstract fun bindIMovieNetworkDataSource(networkDataSource: MoviesNetworkDataSource): IMoviesNetworkDataSource

}