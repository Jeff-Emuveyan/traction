package com.example.movies.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.example.database.ILocalDataSource
import com.example.database.entities.MovieEntity
import com.example.database.entities.MovieEntityRemoteKey
import com.example.network.INetworkDataSource
import com.example.util.toEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

private const val LAST_PAGE = 78
private const val DEFAULT_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(private val localSource: ILocalDataSource,
                           private val remoteSource: INetworkDataSource
): RxRemoteMediator<Int, MovieEntity>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): Single<MediatorResult> {

        val remoteKeySingle: Single<MovieEntityRemoteKey> = when (loadType) {

            LoadType.REFRESH -> Single.just(MovieEntityRemoteKey(DEFAULT_PAGE, DEFAULT_PAGE))

            LoadType.PREPEND -> return Single.just(MediatorResult.Success(true))

            LoadType.APPEND -> getRemoteKeyForLastMovieItemOnTheList(state)
        }

        return remoteKeySingle.subscribeOn(Schedulers.io()).flatMap { remoteKey ->

                if (loadType != LoadType.REFRESH && remoteKey.nextKey == LAST_PAGE) {
                    return@flatMap Single.just<MediatorResult>(MediatorResult.Success(true))
                }

                remoteSource.getMovies(remoteKey.nextKey).map { response ->

                    localSource.database().runInTransaction {
                        if (loadType == LoadType.REFRESH) { localSource.deleteAll() }

                        localSource.insert(MovieEntityRemoteKey(response.page, response.page + 1))
                        localSource.insert(response.results.toEntity(response.page))
                    }

                    return@map MediatorResult.Success(response.page == LAST_PAGE)
                }

            }.onErrorResumeNext { e ->
                return@onErrorResumeNext Single.just(MediatorResult.Error(e))
            }
    }

    private fun getRemoteKeyForLastMovieItemOnTheList(state: PagingState<Int, MovieEntity>): Single<MovieEntityRemoteKey> {
        val lastMovieEntityOnTheList = state.lastItemOrNull()
            ?: return Single.just(MovieEntityRemoteKey(DEFAULT_PAGE, DEFAULT_PAGE))

        return localSource.getRemoteKey(lastMovieEntityOnTheList.pageNumber)
    }

}