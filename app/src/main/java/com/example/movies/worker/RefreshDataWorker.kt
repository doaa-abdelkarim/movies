package com.example.movies.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movies.data.di.MoviesRepo
import com.example.movies.data.di.TVShowsRepo
import com.example.movies.data.remote.apis.APIConstants.Companion.DEFAULT_PAGE_INDEX
import com.example.movies.domain.repositories.BaseVideosRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException

class RefreshDataWorker(
    @ApplicationContext context: Context, params: WorkerParameters,
    @MoviesRepo private val moviesRepository: BaseVideosRepository,
    @TVShowsRepo private val tvShowRepository: BaseVideosRepository
) :
    CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
//            val movies = moviesRepository.getVideos(DEFAULT_PAGE_INDEX)
//            movies.forEach {
//                moviesRepository.getVideoInfo(it.id ?: -1)
//                moviesRepository.getVideoClips(it.id ?: -1)
//                moviesRepository.getVideoReviews(it.id ?: -1, DEFAULT_PAGE_INDEX)
//            }
//
//            val tvShows = tvShowRepository.getVideos(DEFAULT_PAGE_INDEX)
//            tvShows.forEach {
//                tvShowRepository.getVideoInfo(it.id ?: -1)
//                tvShowRepository.getVideoClips(it.id ?: -1)
//                tvShowRepository.getVideoReviews(it.id ?: -1, DEFAULT_PAGE_INDEX)
//            }
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}