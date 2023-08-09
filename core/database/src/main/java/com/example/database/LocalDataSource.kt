package com.example.database

import androidx.paging.PagingSource
import com.example.common.entities.MovieEntity
import com.example.common.entities.MovieEntityRemoteKey
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val appDatabase: AppDatabase): ILocalDataSource {

    override fun database(): AppDatabase {
        return appDatabase
    }

    override fun insert(users: List<MovieEntity>) {
        appDatabase.moviesDao().insert(users)
    }

    override fun insert(remoteKey: MovieEntityRemoteKey) {
        appDatabase.moviesRemoteKeyDao().insert(remoteKey)
    }

    override fun getAll(): PagingSource<Int, MovieEntity> {
        return appDatabase.moviesDao().getAll()
    }

    override fun get(movieTitle: String): Single<MovieEntity?> {
        return appDatabase.moviesDao().get(movieTitle)
    }

    override fun getRemoteKey(pageNumber: Int): Single<MovieEntityRemoteKey> {
       return appDatabase.moviesRemoteKeyDao().getRemoteKey(pageNumber)
    }

    override fun deleteAll() {
        appDatabase.moviesRemoteKeyDao().deleteAll()
        appDatabase.moviesDao().deleteAll()
    }
}