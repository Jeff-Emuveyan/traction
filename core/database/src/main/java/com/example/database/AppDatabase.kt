package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.common.entities.MovieEntity
import com.example.common.entities.MovieEntityRemoteKey
import com.example.database.dao.MoviesDao
import com.example.database.dao.MoviesRemoteKeyDao

@Database(entities = [MovieEntity::class, MovieEntityRemoteKey::class], version = 1)
abstract class AppDatabase: RoomDatabase()  {

    abstract fun moviesDao(): MoviesDao
    abstract fun moviesRemoteKeyDao(): MoviesRemoteKeyDao
}