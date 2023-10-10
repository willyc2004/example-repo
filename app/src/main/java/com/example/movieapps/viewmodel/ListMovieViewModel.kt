package com.example.movieapps.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapps.data.DataSource
import com.example.movieapps.model.Movie
import com.example.movieapps.viewmodel.ListMovieUiState.Loading
import kotlinx.coroutines.launch


sealed interface ListMovieUiState{
    data class Success(val data: List<Movie>): ListMovieUiState
    object Error: ListMovieUiState
    object Loading: ListMovieUiState
}

class ListMovieViewModel: ViewModel() {
    var listMovieUiState: ListMovieUiState by mutableStateOf(Loading)
        private set

    private lateinit var data: List<Movie>

    init{
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {


            try {
                data = DataSource().loadMovie()

                listMovieUiState = ListMovieUiState.Success(data)
            } catch(e: Exception) {
                listMovieUiState = ListMovieUiState.Error
            }
        }
    }

    fun onFavClicked(movie: Movie) {
        movie.isLiked = !movie.isLiked
    }
    //sent server updated movie to server
}