package com.mukesh.rickmortyfan.data.dto.episode

import com.mukesh.rickmortyfan.domain.modal.episode.Episode

data class EpisodeDto(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)

fun EpisodeDto.toEpisode(): Episode {
    return Episode(
        air_date = this.air_date,
        characters = this.characters,
        created = this.created,
        episode = this.episode,
        id = this.id,
        name = this.name,
        url = this.url
    )
}