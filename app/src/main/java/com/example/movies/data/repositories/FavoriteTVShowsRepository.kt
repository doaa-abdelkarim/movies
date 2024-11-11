package com.example.movies.data.repositories

import com.example.movies.data.local.db.dao.FavoriteTVShowsDao
import com.example.movies.data.local.models.favorites.BaseLocalFavorite
import com.example.movies.data.local.models.favorites.LocalFavoriteTVShow
import com.example.movies.data.local.models.videos.tvshows.asDomainModel
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseFavoriteRepository

class FavoriteTVShowsRepository(
    private val favoriteTVShowsDao: FavoriteTVShowsDao
) : BaseFavoriteRepository {

    override suspend fun cacheFavorites(baseLocalFavorite: BaseLocalFavorite) {
        favoriteTVShowsDao.insert(baseLocalFavorite as LocalFavoriteTVShow)
    }

    override suspend fun getAllFavorites(): List<Video> {
        return favoriteTVShowsDao.getAllFavoritesTVShows().asDomainModel()
    }

}
