package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.database.entity.FavoritesDao
import com.mukesh.rickmortyfan.data.database.entity.toEpisode
import com.mukesh.rickmortyfan.data.database.entity.toEpisodeEntity
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.domain.repository.FavoriteEpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteEpisodeRepositoryImpl(val favoritesDao: FavoritesDao) : FavoriteEpisodeRepository {
    override fun getFavoriteEpisodes(): Flow<List<Episode>> = flow {
        favoritesDao.getFavoriteEpisodes().forEach {
            it.toEpisode()
        }
    }

    override suspend fun addFavoriteEpisode(episode: Episode): Long {
        return favoritesDao.addFavoriteEpisode(episode.toEpisodeEntity())
    }

    override suspend fun removeFavoriteEpisode(episode: Episode): Int {
        return favoritesDao.removeFavoriteEpisode(episode.toEpisodeEntity())
    }

    override suspend fun isFavoriteEpisodePresent(id: Int): Boolean {
        return favoritesDao.isFavoriteEpisodePresent(id)
    }
}