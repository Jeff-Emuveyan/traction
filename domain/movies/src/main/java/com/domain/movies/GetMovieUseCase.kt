package com.domain.movies

import com.example.common.model.Movie
import com.example.movies.IMoviesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val moviesRepository: IMoviesRepository): IGetMovieUseCase {

    override fun invoke(movieTitle: String): Single<Movie?> {
        return moviesRepository.get(movieTitle).map { it.toMovie() }
    }
}