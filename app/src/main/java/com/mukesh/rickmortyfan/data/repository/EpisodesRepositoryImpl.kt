package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.dto.episode.toEpisode
import com.mukesh.rickmortyfan.data.dto.episode.toEpisodes
import com.mukesh.rickmortyfan.data.retrofit.RickMortyEpisodesApi
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.domain.modal.episode.Episodes
import com.mukesh.rickmortyfan.domain.repository.EpisodesRepository
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(val episodesApi: RickMortyEpisodesApi) :
    EpisodesRepository {
    override suspend fun getAllEpisodes(): Episodes {
        return episodesApi.getAllEpisodes().toEpisodes()
    }

    override suspend fun getEpisodeDetail(episodeId: String): Episode {
        return episodesApi.getEpisodeDetail(episodeId).toEpisode()
    }

    override suspend fun getMultipleEpisodes(ids: List<String>): List<Episode> {
        return episodesApi.getMultipleEpisodes(ids.joinToString()).map {
            it.toEpisode()
        }
    }
}