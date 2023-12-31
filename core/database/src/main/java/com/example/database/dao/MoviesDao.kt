package com.example.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.common.entities.MovieEntity
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    fun getAll(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE title LIKE :movieTitle")
    fun get(movieTitle: String): Single<MovieEntity?>

    @Query("DELETE FROM movies")
    fun deleteAll()
}