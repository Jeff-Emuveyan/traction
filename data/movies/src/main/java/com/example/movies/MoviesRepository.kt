package com.example.movies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.database.ILocalDataSource
import com.example.database.entities.MovieEntity
import com.example.movies.paging.MoviesRemoteMediator
import com.example.network.movies.IMoviesNetworkDataSource
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val networkSource: IMoviesNetworkDataSource,
    private val localSource: ILocalDataSource): IMoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = MoviesRemoteMediator(localSource, networkSource)
    ) {
        localSource.getAll()
    }

    override fun getMovies(pageNumber: Int): Pager<Int, MovieEntity> {
        return pager
    }

}