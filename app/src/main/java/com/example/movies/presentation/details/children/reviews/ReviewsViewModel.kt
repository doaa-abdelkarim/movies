package com.example.movies.presentation.details.children.reviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Review
import com.example.movies.domain.repositories.BaseMoviesRepository
import com.example.movies.util.constants.AppConstants.Companion.KEY_LAST_EMITTED_VALUE
import com.example.movies.util.constants.AppConstants.Companion.KEY_STATE_SELECTED_MOVIE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val baseMoviesRepository: BaseMoviesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedMovie = savedStateHandle.get<Movie>(KEY_STATE_SELECTED_MOVIE)
    private val _reviews = MutableStateFlow<PagingData<Review>>(PagingData.empty())
    val reviews = _reviews.asStateFlow()

    init {
        selectedMovie?.let { getMovieReviews(it) }
    }

    fun getMovieReviews(selectedMovie: Movie, isLargeScreen: Boolean) {
        // Retrieve the last emitted value from SavedStateHandle
        val lastEmittedValue = savedStateHandle.get<Movie?>(KEY_LAST_EMITTED_VALUE)
        // Only send request if the current value is different from the last one stored
        if (lastEmittedValue == null || lastEmittedValue != selectedMovie) {
            getMovieReviews(
                selectedMovie = selectedMovie,
                doForLargeScreen = {
                    savedStateHandle[KEY_LAST_EMITTED_VALUE] = selectedMovie
                }
            )
        }
    }

    private fun getMovieReviews(
        selectedMovie: Movie,
        doForLargeScreen: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            //As I see without caching it does not survive configuration even if we cache in Room
            val reviews = if (selectedMovie.isMovie)
                baseMoviesRepository.getMovieReviews(selectedMovie.id).cachedIn(viewModelScope)
            else
                baseMoviesRepository.getTVShowReviews(selectedMovie.id).cachedIn(viewModelScope)
            reviews.distinctUntilChanged()
                .collectLatest {
                    _reviews.value = it
                    doForLargeScreen?.invoke()
                }
        }
    }
}
