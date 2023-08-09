package com.example.movies.util
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.common.model.Movie

class MoviePreviewParameter: PreviewParameterProvider<Movie> {

    override val values: Sequence<Movie> = sequenceOf(
        Movie(
            "Lion King",
            "2023-04-01",
            "https://image.tmdb.org/t/p/w500/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg",
            "This is a default overview that was created for demonstration purposes.",
            "", "", 0.0, 0.0, 0
        )
    )
}