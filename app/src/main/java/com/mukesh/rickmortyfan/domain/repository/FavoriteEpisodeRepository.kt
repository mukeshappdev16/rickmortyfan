package com.mukesh.rickmortyfan.domain.repository

import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import kotlinx.coroutines.flow.Flow

interface FavoriteEpisodeRepository {
    fun getFavoriteEpisodes(): Flow<List<Episode>>

    suspend fun addFavoriteEpisode(episode: Episode): Long

    suspend fun removeFavoriteEpisode(episode: Episode): Int

    suspend fun isFavoriteEpisodePresent(id: Int): Boolean
}
