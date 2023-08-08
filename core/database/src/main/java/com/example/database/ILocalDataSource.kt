package com.example.database

import androidx.paging.PagingSource
import com.example.database.entities.MovieEntity
import com.example.database.entities.MovieEntityRemoteKey
import io.reactivex.Single

interface ILocalDataSource {

    fun database(): AppDatabase

    fun insertAll(users: List<MovieEntity>)

    fun pagingSource(query: Int): PagingSource<Int, MovieEntity>

    fun getAll(): PagingSource<Int, MovieEntity>

    fun clearAll()

    fun insertOrReplace(remoteKey: MovieEntityRemoteKey)

    fun remoteKeyByQuery(pageNumber: Int): Single<MovieEntityRemoteKey>

    fun deleteAll()
}