package com.example.database

import androidx.paging.PagingSource
import com.example.common.entities.MovieEntity
import com.example.common.entities.MovieEntityRemoteKey
import io.reactivex.Single

interface ILocalDataSource {

    fun database(): AppDatabase

    fun insert(users: List<MovieEntity>)

    fun insert(remoteKey: MovieEntityRemoteKey)

    fun getAll(): PagingSource<Int, MovieEntity>

    fun getRemoteKey(pageNumber: Int): Single<MovieEntityRemoteKey>

    fun deleteAll()
}