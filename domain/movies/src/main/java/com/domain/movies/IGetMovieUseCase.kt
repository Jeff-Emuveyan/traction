package com.domain.movies

import com.example.common.model.Movie
import io.reactivex.Single

interface IGetMovieUseCase {

    operator fun invoke(movieTitle: String): Single<Movie?>
}