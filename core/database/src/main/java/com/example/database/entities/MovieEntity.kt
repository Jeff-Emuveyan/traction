package com.example.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    val title: String?,
    val overview: String?,
    val posterUrl: String?,
    val releaseDate: String?,
    val pageNumber: Int,
    @PrimaryKey(autoGenerate = true)
    val index:Int = 0,
)