package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.MoviesDao
import com.example.database.dao.MoviesRemoteKeyDao
import com.example.database.entities.MovieEntity
import com.example.database.entities.MovieEntityRemoteKey

@Database(entities = [MovieEntity::class, MovieEntityRemoteKey::class], version = 1)
abstract class AppDatabase: RoomDatabase()  {

    abstract fun moviesDao(): MoviesDao
    abstract fun moviesRemoteKeyDao(): MoviesRemoteKeyDao
}