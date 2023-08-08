package com.example.movies.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.database.entities.MovieEntity
import com.example.movies.R
import com.example.movies.model.MoviesScreenUiState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesScreen(viewModel: MoviesScreenViewModel = hiltViewModel()) {

    val listState = rememberLazyListState()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val movies = viewModel.getMovies().flow.collectAsLazyPagingItems()

    val refreshing = uiState.value is MoviesScreenUiState.Loading
    val refreshingState = rememberPullRefreshState(refreshing, { })

    MoviesScreen(movies, uiState.value, listState, refreshing, refreshingState) {}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MoviesScreen(
    list: LazyPagingItems<MovieEntity>,
    moviesScreenUiState: MoviesScreenUiState,
    listState: LazyListState,
    refreshing: Boolean,
    refreshingState: PullRefreshState,
    onRetry:() -> Unit) {

    Box(modifier = Modifier.pullRefresh(refreshingState)) {

        when (moviesScreenUiState) {
            is MoviesScreenUiState.NetworkError -> { ErrorButton(onRetry) }
            is MoviesScreenUiState.Success -> { MovieList(listState = listState, list)}
            else -> {}
        }

        PullRefreshIndicator(refreshing, refreshingState, modifier = Modifier
            .align(Alignment.TopCenter))

        MovieList(listState = listState, list)
    }
}

@Composable
internal fun MovieList(listState: LazyListState, lazyPagingItems: LazyPagingItems<MovieEntity>) {
    if (lazyPagingItems.itemCount > 0) {
        LazyColumn(state = listState) {
            items(count = lazyPagingItems.itemCount) { index ->
                val item = lazyPagingItems[index]
                item?.let { Movie(it) }
            }
        }
    }
}

@Composable
//@Preview(showBackground = true, showSystemUi = true)
internal fun Movie(movie: MovieEntity) {
    Column {
        AsyncImage(
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            model = movie.posterUrl,
            contentDescription = movie.title,
            placeholder = painterResource(R.drawable.image_24)
        )
        Column(modifier = Modifier.padding(8.0.dp)) {
            Text(text = movie.title ?: "", style = MaterialTheme.typography.headlineLarge)
            Text(text = movie.releaseDate ?: "")
            Text(text = movie.overview ?: "", textAlign = TextAlign.Justify, modifier = Modifier.padding(top = 8.0.dp))

            Divider(thickness = 1.dp, modifier = Modifier.padding(8.0.dp))
        }
    }
}

@Composable
internal fun ErrorButton(onRetry:() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExtendedFloatingActionButton(
            onClick = onRetry,
            backgroundColor = MaterialTheme.colorScheme.error,
            icon = { Icon(Icons.Filled.Refresh, stringResource(id = R.string.error_message)) },
            text = { Text(text = stringResource(id = R.string.error_message)) },
        )
    }
}
