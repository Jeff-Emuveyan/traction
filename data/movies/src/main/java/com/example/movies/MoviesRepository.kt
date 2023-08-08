package com.example.movies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.common.entities.MovieEntity
import com.example.database.ILocalDataSource
import com.example.movies.paging.MoviesRemoteMediator
import com.example.network.INetworkDataSource
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val networkSource: INetworkDataSource,
    private val localSource: ILocalDataSource): IMoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    private val pager = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = MoviesRemoteMediator(localSource, networkSource)
    ) {
        localSource.getAll()
    }

    override fun getMovies(): Pager<Int, MovieEntity> = pager
}