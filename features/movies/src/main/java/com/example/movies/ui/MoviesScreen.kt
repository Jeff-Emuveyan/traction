package com.example.movies.ui

import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.common.model.Movie
import com.example.movies.R
import com.example.movies.util.MoviePreviewParameter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesScreen(viewModel: MoviesScreenViewModel = hiltViewModel()) {

    val listState = rememberLazyListState()
    val movies = viewModel.getMovies().collectAsLazyPagingItems()

    val refreshing = false
    val refreshingState = rememberPullRefreshState(refreshing, { })

    MoviesScreen(movies, listState, refreshing, refreshingState) {}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MoviesScreen(
    list: LazyPagingItems<Movie>,
    listState: LazyListState,
    refreshing: Boolean,
    refreshingState: PullRefreshState,
    onRetry:() -> Unit) {

    Box(modifier = Modifier.pullRefresh(refreshingState)) {
        var text by remember { mutableStateOf("") }

        PullRefreshIndicator(refreshing, refreshingState, modifier = Modifier
            .align(Alignment.TopCenter))

        Column {
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                ),
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                singleLine = true,
                label = { Text("Search by title") }
            )
            MovieList(listState = listState, list)
        }
    }
}

@Composable
internal fun MovieList(listState: LazyListState, lazyPagingItems: LazyPagingItems<Movie>) {
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
@Preview(showBackground = true, showSystemUi = true)
internal fun Movie(@PreviewParameter(MoviePreviewParameter::class) movie: Movie) {
    Column {
        AsyncImage(
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            model = movie.posterUrl,
            contentDescription = movie.title,
            placeholder = painterResource(R.drawable.image_24)
        )
        Column(modifier = Modifier.padding(8.0.dp)) {
            Text(text = movie.title, style = MaterialTheme.typography.titleLarge )
            Text(text = movie.releaseDate)
            Text(text = movie.overview, textAlign = TextAlign.Justify, modifier = Modifier.padding(top = 8.0.dp))

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
