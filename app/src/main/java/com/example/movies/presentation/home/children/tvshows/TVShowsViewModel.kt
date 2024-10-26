package com.example.movies.presentation.home.children.tvshows

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.data.di.TVShowsRepo
import com.example.movies.domain.entities.BaseVideo
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.presentation.home.base.VideosViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TVShowsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    @TVShowsRepo private val videosRepository: BaseVideosRepository,
) : VideosViewModel(context) {

    override val videosFlow: Flow<PagingData<BaseVideo>> = getVideos()

    override fun getVideos(): Flow<PagingData<BaseVideo>> =
        //As I see without caching it does not survive configuration even if we cache in Room
        videosRepository.getVideos().cachedIn(viewModelScope)
}
