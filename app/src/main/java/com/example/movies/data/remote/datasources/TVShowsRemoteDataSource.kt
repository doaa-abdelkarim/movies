package com.example.movies.data.remote.datasources

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.asDomainModel
import com.example.movies.data.remote.models.asTVShowDomainModel
import com.example.movies.data.paging.ReviewPagingSource
import com.example.movies.data.paging.VideosPagingSource
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.VideoType.TVSHOW
import com.example.movies.util.getDefaultPageConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface BaseTVShowsRemoteDataSource : BaseVideosRepository

class TVShowsRemoteDataSource(
    private val moviesAPI: MoviesAPI
) : BaseTVShowsRemoteDataSource {
    override fun getVideos(): Flow<PagingData<Video>> {
//        return moviesAPI.getTVShows().asTVShowDomainModel()
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = {
                VideosPagingSource(
                    moviesAPI = moviesAPI,
                    videoType = TVSHOW
                )
            }
        ).flow.map {
            it.map { video -> video.asTVShowDomainModel() }
        }
    }

    override suspend fun getVideoInfo(videoId: Int): Video {
        return moviesAPI.getTVShowInfo(videoId).asDomainModel()
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        return moviesAPI.getTVShowClips(videoId).asDomainModel()
    }

    override fun getVideoReviews(videoId: Int): Flow<PagingData<Review>> {
//        return moviesAPI.getTVShowReviews(videoId).asDomainModel()
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = {
                ReviewPagingSource(
                    moviesAPI = moviesAPI,
                    videoType = TVSHOW,
                    videoId = videoId
                )
            }
        ).flow.map {
            it.map { review -> review.asDomainModel(videoId = videoId) }
        }
    }

    override suspend fun cacheVideos(videos: List<Video>) {
        TODO("Not yet implemented")
    }

    override suspend fun cacheVideoDetails(video: Video) {
        TODO("Not yet implemented")
    }

    override suspend fun cacheVideoClips(clips: List<Clip>) {
        TODO("Not yet implemented")
    }

    override suspend fun cacheVideoReviews(reviews: List<Review>) {
        TODO("Not yet implemented")
    }

}