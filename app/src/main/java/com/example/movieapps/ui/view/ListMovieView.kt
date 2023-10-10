package com.example.movieapps.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapps.model.Movie
import com.example.movieapps.viewmodel.ListMovieUiState
import com.example.movieapps.viewmodel.ListMovieViewModel

@Composable
fun ListMovieView(movieList: List<Movie>, onFavClicked: (Movie) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(movieList) { movie ->
            var isLikedView by rememberSaveable {
                mutableStateOf(movie.isLiked)
            }
            MovieCard(
                movie,
                Modifier
                    .padding(8.dp)
                    .fillMaxSize(), onFavClicked = {
                    onFavClicked(movie)
                    isLikedView = movie.isLiked
                },
                isLikedView = isLikedView
            )
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie, modifier: Modifier = Modifier, onFavClicked: () -> Unit, isLikedView: Boolean

) {
    Card(
        modifier = modifier
    ) {
        Column {
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = movie.posterPath),
                    contentDescription = "Movie Poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.FillWidth
                )

                FloatingActionButton(
                    onClick = onFavClicked,
                    shape = CircleShape,
                    modifier = Modifier.padding(end = 5.dp, bottom = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorite",
                        tint = if (isLikedView) {
                            Color.Red
                        } else {
                            Color.LightGray
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = movie.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .weight(2f)
                        .height(60.dp),
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Justify
                )

                Text(
                    text = "(${movie.getYear()})",
                    textAlign = TextAlign.Right,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star",
                    tint = Color(0xFFFDCC0D)
                )
                Text(
                    text = "${movie.voteAverage}/10.0", modifier = Modifier.padding(start = 4.dp)
                )
            }

            Text(
                text = movie.overview,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListMoviePreview() {
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