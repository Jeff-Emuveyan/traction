package com.example.database.di

import androidx.paging.PagingSource
import com.example.database.AppDatabase
import com.example.database.ILocalDataSource
import com.example.database.LocalDataSource
import com.example.database.entities.MovieEntity
import com.example.database.entities.MovieEntityRemoteKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSourceModuleImpl @Inject constructor(private val localDataSource: LocalDataSource): ILocalDataSource {
    override fun database(): AppDatabase {
        return localDataSource.database()
    }

    override fun insert(users: List<MovieEntity>) {
        localDataSource.insert(users)
    }

    override fun insert(remoteKey: MovieEntityRemoteKey) {
        localDataSource.insert(remoteKey)
    }

    override fun getAll(): PagingSource<Int, MovieEntity> {
        return localDataSource.getAll()
    }

    override fun getRemoteKey(pageNumber: Int): Single<MovieEntityRemoteKey> {
        return localDataSource.getRemoteKey(pageNumber)
    }

    override fun deleteAll() {
        localDataSource.deleteAll()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindApi(impl: LocalDataSourceModuleImpl): ILocalDataSource
}