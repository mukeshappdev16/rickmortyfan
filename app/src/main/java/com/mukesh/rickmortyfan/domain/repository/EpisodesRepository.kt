package com.mukesh.rickmortyfan.domain.repository

import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.domain.modal.episode.Episodes

interface EpisodesRepository {
    suspend fun getAllEpisodes(page: Int): Episodes
    suspend fun getEpisodeDetail(episodeId: String): Episode
    suspend fun getMultipleEpisodes(ids: List<String>): List<Episode>
}