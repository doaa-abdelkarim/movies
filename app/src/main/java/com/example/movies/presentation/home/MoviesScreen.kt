package com.example.movies.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.home.base.VideosEvent
import com.example.movies.presentation.home.children.movies.MoviesViewModel
import com.example.movies.util.exhaustive

@Composable
fun MoviesScreen(navigateToDetailsScreen: (Movie) -> Unit) {
    val moviesViewModel: MoviesViewModel = hiltViewModel()
    val movies = moviesViewModel.videosFlow.collectAsLazyPagingItems()
    LaunchedEffect(true) {
        moviesViewModel.videosEvent.collect {
            when (it) {
                is VideosEvent.NavigateToDetailsScreen -> navigateToDetailsScreen(it.video)
            }.exhaustive
        }
    }
    ListMovies(
        movies = movies,
        onItemClick = { movie -> moviesViewModel.onVideoClicked(movie) }
    )
}