package com.example.movies.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.example.database.ILocalDataSource
import com.example.database.entities.MovieEntity
import com.example.database.entities.MovieEntityRemoteKey
import com.example.network.movies.IMoviesNetworkDataSource
import com.example.util.toEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(private val localSource: ILocalDataSource,
                           private val remoteSource: IMoviesNetworkDataSource): RxRemoteMediator<Int, MovieEntity>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): Single<MediatorResult> {

        var remoteKeySingle: Single<MovieEntityRemoteKey>? = null
        remoteKeySingle = when (loadType) {

            LoadType.REFRESH -> {
                Log.e("JEFF", "REFRESH")
                Single.just(MovieEntityRemoteKey(1, 1))
            }

            LoadType.PREPEND -> return Single.just(MediatorResult.Success(true))

            LoadType.APPEND -> {
                Log.e("JEFF", "APPEND")
                getRemoteKeyForLastMovieItemOnTheList(state)
            }
        }

        val x = remoteKeySingle.subscribeOn(Schedulers.io()).flatMap { remoteKey ->

                if (loadType != LoadType.REFRESH && remoteKey.nextKey == 78) {
                    Log.e("JEFF", "111111111111")
                    return@flatMap Single.just<MediatorResult>(MediatorResult.Success(true))
                }

                remoteSource.getMovies(remoteKey.nextKey).map { response ->

                    localSource.database().runInTransaction {
                        if (loadType == LoadType.REFRESH) {
                            localSource.deleteAll()
                        }

                        Log.e("TOSIN", "HERE ${response.page}")
                        localSource.insertOrReplace(MovieEntityRemoteKey(response.page, response.page + 1))
                        localSource.insertAll(response.results.toEntity(response.page))
                    }

                    Log.e("JEFF", "2222222 ${response.page == 78}")
                    return@map MediatorResult.Success(response.page == 78)
                }

            }.onErrorResumeNext {e ->
            Log.e("JEFF", "3333333")
                return@onErrorResumeNext Single.just(MediatorResult.Error(e))
            }

        return x
    }

    private fun getRemoteKeyForLastMovieItemOnTheList(state: PagingState<Int, MovieEntity>): Single<MovieEntityRemoteKey> {
        val lastMovieEntityOnTheList = state.lastItemOrNull()
            ?: return Single.just(MovieEntityRemoteKey(1, 1))

        val remoteKeyForLastMovieEntityOnTheList = localSource.remoteKeyByQuery(lastMovieEntityOnTheList.pageNumber)
        return remoteKeyForLastMovieEntityOnTheList
    }

}