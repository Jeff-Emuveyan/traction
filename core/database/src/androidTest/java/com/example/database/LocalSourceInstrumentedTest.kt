package com.example.database

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.common.entities.MovieEntity
import com.example.common.entities.MovieEntityRemoteKey
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LocalSourceInstrumentedTest {

    private lateinit var db: AppDatabase
    private lateinit var localDataSource: LocalDataSource

    private val movies = listOf<MovieEntity>(
        MovieEntity("Saw", "2022-01-2", "", "",
            "", "1992-09-10", 0.0, 0.0,0, 1),
        MovieEntity("Lion King", "", "", "",
            "", "", 0.0, 0.0,0, 1),
        MovieEntity("Cinderella", "2000-01-02", "", "",
            "", "", 0.0, 0.0,0, 1)
    )

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        localDataSource = LocalDataSource(db)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun can_save_movies() {
        localDataSource.insert(movies)

        val aSavedMovie = localDataSource.get("Saw")?.blockingGet()
        Assert.assertEquals("Saw", aSavedMovie?.title)
    }

    @Test
    fun can_save_remoteKeys() {
        val aKey = MovieEntityRemoteKey(1, 22)

        localDataSource.insert(aKey)

        val aSavedKey = localDataSource.getRemoteKey(1).blockingGet()
        Assert.assertEquals(22, aSavedKey.nextKey)
    }

}