package com.example.movies.ui

import androidx.lifecycle.ViewModel
import com.domain.movies.IGetMovieUseCase
import com.domain.movies.IGetMoviesUseCase
import com.example.common.model.Movie
import com.example.movies.model.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val disposable: CompositeDisposable,
    private val getMovies: IGetMoviesUseCase,
    private val getMovie: IGetMovieUseCase): ViewModel() {

    private val _searchResult = MutableStateFlow<SearchResult>(SearchResult.NoResult)
    val searchResult = _searchResult.asStateFlow()

    fun getMovies() = getMovies.invoke()

    fun search(movieTitle: String) {
        disposable.add(
            getMovie(movieTitle)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                  processResult(it)
                }, {
                   processResult(null)
                })
        )
    }

    private fun processResult(movie: Movie?) {
        if (movie == null) {
            _searchResult.value = SearchResult.NoResult
        } else {
            _searchResult.value = SearchResult.Result(movie)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}