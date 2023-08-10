package com.example.movies.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.common.model.Movie
import com.example.movies.R
import com.example.movies.model.SearchResult
import com.example.movies.util.MoviePreviewParameter
import kotlinx.coroutines.delay

@Composable
fun MoviesScreen(viewModel: MoviesScreenViewModel = hiltViewModel()) {

    val listState = rememberLazyListState()
    val listOfMovies = viewModel.getMovies().collectAsLazyPagingItems()
    val searchResult = viewModel.searchResult.collectAsStateWithLifecycle().value

    MoviesScreen(
        listOfMovies,
        searchResult,
        listState,
    ) { viewModel.search(it) }
}

@Composable
internal fun MoviesScreen(
    list: LazyPagingItems<Movie>,
    searchResult: SearchResult,
    listState: LazyListState,
    onSearch:(String) -> Unit) {

    Box {

        var movieSearchTitle by remember { mutableStateOf("") }
        var isSearching by remember { mutableStateOf(false) }

        Column {
            if (isSearching) {
                LinearProgressIndicator(modifier = Modifier.padding(8.0.dp).fillMaxWidth())
            }
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                ),
                modifier = Modifier.fillMaxWidth(),
                value = movieSearchTitle,
                onValueChange = { movieSearchTitle = it },
                singleLine = true,
                label = { Text(stringResource(id = R.string.search_message)) }
            )
            MovieList(listState = listState, list, searchResult)
        }

        LaunchedEffect(key1 = movieSearchTitle) {
            isSearching = true
            delay(2000) // add debounce to the coroutine scope
            onSearch(movieSearchTitle)
            isSearching = false
        }
    }
}

@Composable
internal fun MovieList(
    listState: LazyListState,
    lazyPagingItems: LazyPagingItems<Movie>,
    searchResult: SearchResult
) {
    val endlessListOfMoviesAvailable = lazyPagingItems.itemCount > 0
    val movieSearchResultAvailable = searchResult is SearchResult.Result

    if (movieSearchResultAvailable) {
        val movie = (searchResult as SearchResult.Result).movie
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Movie(movie)
        }
    } else if (endlessListOfMoviesAvailable) {
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
    var openMovieDetails by remember { mutableStateOf(false) }

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
            MovieDetails(openMovieDetails, movie)
            SeeMoreText { openMovieDetails = !openMovieDetails }
        }
    }
}

@Composable
internal fun MovieDetails(open: Boolean, movie: Movie) {

    AnimatedVisibility(
        visible = open,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(modifier = Modifier.padding(top = 12.0.dp)) {
            AsyncImage(
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(),
                model = movie.backdropPath,
                contentDescription = movie.title,
                placeholder = painterResource(R.drawable.image_24)
            )
            Column(Modifier.padding(top = 8.0.dp)) {
                Text(text = movie.title)
                Text(text = "${stringResource(id = R.string.vote_avg)} ${movie.voteAverage}")
                Text(text = "${stringResource(id = R.string.popularity)} ${movie.popularity}")
                Text(text = "${stringResource(id = R.string.language)} ${movie.language}")
            }
        }
    }
}

@Composable
internal fun SeeMoreText(onclick: () -> Unit) {
    var open by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(8.0.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = if (open) stringResource(id = R.string.close) else stringResource(id = R.string.see_more),
            modifier = Modifier.clickable(onClick = {
                open = !open
                onclick()
        }), color = Color(0xFF7695F0)
        )
    }

    Spacer(modifier = Modifier.height(28.0.dp))
}
