package com.example.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_remote_keys")
class MovieEntityRemoteKey(
    @PrimaryKey
    val pageNumber: Int,
    val nextKey: Int
    )