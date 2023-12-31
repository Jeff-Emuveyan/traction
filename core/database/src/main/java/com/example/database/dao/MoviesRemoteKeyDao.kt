package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.common.entities.MovieEntityRemoteKey
import io.reactivex.Single

@Dao
interface MoviesRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(remoteKey: MovieEntityRemoteKey)

    @Query("SELECT * FROM movies_remote_keys WHERE pageNumber = :pageNumber")
    fun getRemoteKey(pageNumber: Int): Single<MovieEntityRemoteKey>

    @Query("DELETE FROM movies_remote_keys")
    fun deleteAll()
}