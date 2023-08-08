package com.example.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.common.model.Movie

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
@Entity(tableName = "movies")
data class MovieEntity(
    val title: String?,
    val overview: String?,
    val posterUrl: String?,
    val releaseDate: String?,
    val pageNumber: Int,
    @PrimaryKey(autoGenerate = true)
    val index:Int = 0,
) {

    fun toMovie(): Movie {
        return Movie(
            title ?:"",
            releaseDate ?: "",
            "$IMAGE_BASE_URL$posterUrl",
            overview ?: ""
        )
    }
}