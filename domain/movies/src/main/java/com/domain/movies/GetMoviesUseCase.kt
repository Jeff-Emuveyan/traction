package com.domain.movies

import androidx.paging.map
import com.example.movies.IMoviesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: IMoviesRepository): IGetMoviesUseCase {

    override operator fun invoke() = moviesRepository.getMovies().flow.map {
        it.map { entity -> entity.toMovie() }
    }

}