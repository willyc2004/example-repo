package com.example.movieapps.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapps.data.DataSource
import com.example.movieapps.model.Movie
import kotlinx.coroutines.launch


sealed interface MovieDetailUIState{
    data class Success(val data: Movie):MovieDetailUIState
    object Error: MovieDetailUIState
    object Loading: MovieDetailUIState
}

class MovieDetailViewModel: ViewModel() {
    var movieDetailUIState: MovieDetailUIState by mutableStateOf(MovieDetailUIState.Loading)
        private set

    private lateinit var data: Movie

    fun getMovieById(id:Int) {
        viewModelScope.launch {
            for (temp in DataSource().loadMovie()){
                if (temp.id == id) {
                    data = temp
                    break
                }
            }
            movieDetailUIState = MovieDetailUIState.Success(data)
        }
    }
}