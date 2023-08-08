package com.example.database

import androidx.paging.PagingSource
import com.example.database.entities.MovieEntity
import com.example.database.entities.MovieEntityRemoteKey
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val appDatabase: AppDatabase): ILocalDataSource {

    override fun database(): AppDatabase {
        return appDatabase
    }

    override fun insertAll(users: List<MovieEntity>) {
        appDatabase.moviesDao().insertAll(users)
    }

    override fun pagingSource(query: Int): PagingSource<Int, MovieEntity> {
        return appDatabase.moviesDao().pagingSource(query)
    }

    override fun getAll(): PagingSource<Int, MovieEntity> {
        return appDatabase.moviesDao().getAll()
    }

    override fun clearAll() {
        appDatabase.moviesDao().clearAll()
    }

    override fun insertOrReplace(remoteKey: MovieEntityRemoteKey) {
        appDatabase.moviesRemoteKeyDao().insertOrReplace(remoteKey)
    }

    override fun remoteKeyByQuery(pageNumber: Int): Single<MovieEntityRemoteKey> {
       return appDatabase.moviesRemoteKeyDao().remoteKeyByQuery(pageNumber)
    }

    override fun deleteAll() {
        appDatabase.moviesRemoteKeyDao().deleteAll()
        appDatabase.moviesDao().deleteAll()
    }
}