package com.mukesh.rickmortyfan.data.dto.episode

import com.mukesh.rickmortyfan.domain.modal.episode.Episodes

data class EpisodesResponse(
    val info: InfoDto,
    val results: List<EpisodeDto>,
)

fun EpisodesResponse.toEpisodes(): Episodes {
    return Episodes(
        info = this.info.toInfo(),
        episodes = this.results.map { it.toEpisode() },
    )
}
