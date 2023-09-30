package com.example.movies.util

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.paging.compose.LazyPagingItems
import com.example.common.model.Movie
import com.example.movies.model.SearchResult


/**
 * Use this link to learn about Plain state holders:
 * https://dladukedev.com/articles/006_stateholders/
 * https://medium.com/@joseph.1hach/stop-using-viewmodel-to-manage-your-state-feb4abfaa9b4
 *
 * Watch this video to see how they can use 'rememberSaveable' instead of 'remember' in a plain state holder:
 * https://youtu.be/TbxCz5AljQk?si=phc1LPVgP9Dgm10B
 *
 * Finally, see this link after you have watched the video concerning rememberSaveable:
 * https://developer.android.com/jetpack/compose/state#restore-ui-state
 *
 * */
@Composable
fun rememberMoviesScreenState(
    list: LazyPagingItems<Movie>,
    searchResult: SearchResult,
    listState: LazyListState = rememberLazyListState()
): MoviesScreenState = remember (searchResult.movieSearchTitle) {

    MoviesScreenState (
        searchResult.movieSearchTitle,
        list,
        searchResult,
        listState
    )
}

class MoviesScreenState (
    searchTitle: String,
    val list: LazyPagingItems<Movie>,
    val searchResult: SearchResult,
    val listState: LazyListState,
) {
    var movieSearchTitle by mutableStateOf(searchTitle)
    var isSearching by mutableStateOf(false)
}
