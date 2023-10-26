package com.example.traction.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.example.movies.ui.MoviesScreen
import com.example.traction.ui.theme.TractionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TractionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().semantics {
                        testTagsAsResourceId = true
                    },
                    color = MaterialTheme.colorScheme.background
                ) {
                    MoviesScreen()
                }
            }
        }
    }
}