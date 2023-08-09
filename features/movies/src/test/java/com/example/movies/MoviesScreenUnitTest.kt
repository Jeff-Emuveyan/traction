package com.example.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import com.domain.movies.IGetMovieUseCase
import com.domain.movies.IGetMoviesUseCase
import com.example.common.model.Movie
import com.example.movies.ui.MoviesScreenViewModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class MoviesScreenUnitTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var compositeDisposable: CompositeDisposable
    @MockK
    private lateinit var getMovieUseCase: IGetMovieUseCase
    @MockK
    private lateinit var getMoviesUseCase: IGetMoviesUseCase

    private val movieA = Movie(
        "Lion King",
        "2023-04-01",
        "https://image.tmdb.org/t/p/w500/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg",
        "This is a default overview that was created for demonstration purposes.",
        "", "", 0.0, 0.0, 0
    )
    private val movieB = Movie(
        "Titanic",
        "2023-04-01",
        "https://image.tmdb.org/t/p/w500/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg",
        "This is a default overview that was created for demonstration purposes.",
        "", "", 0.0, 0.0, 0
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testThatViewModelCanReturnAListOfPaginatedMovies() = runTest {

        every { getMoviesUseCase.invoke() } returns paginatedResult()

        val viewModel = MoviesScreenViewModel(compositeDisposable, getMoviesUseCase, getMovieUseCase)
        val pagedMovies = viewModel.getMovies()
        val listOfMovies = pagedMovies.asSnapshot()

        assertEquals(2, listOfMovies.size)
    }

    private fun paginatedResult(): kotlinx.coroutines.flow.Flow<PagingData<Movie>> {
        val list = listOf(movieA, movieB)
        val pagingSourceFactory = list.asPagingSourceFactory()
        val pagingSource = pagingSourceFactory()

        return Pager(
            config = PagingConfig(pageSize = 20),
            initialKey = null,
            pagingSourceFactory = { pagingSource }
        ).flow
    }
}