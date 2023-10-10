package com.example.movieapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapps.data.DataSource
import com.example.movieapps.ui.theme.MovieAppsTheme
import com.example.movieapps.ui.view.ListMovieView
import com.example.movieapps.viewmodel.ListMovieUiState
import com.example.movieapps.viewmodel.ListMovieViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val listMovieViewModel: ListMovieViewModel = viewModel()
                    val status = listMovieViewModel.listMovieUiState
                    when (status) {
                        is ListMovieUiState.Loading -> {}
                        is ListMovieUiState.Success -> ListMovieView(movieList = status.data, onFavClicked = {
                            listMovieViewModel.onFavClicked(it)
                        })

                        is ListMovieUiState.Error -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun App() {

}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MovieAppsTheme {
        App()
    }
}