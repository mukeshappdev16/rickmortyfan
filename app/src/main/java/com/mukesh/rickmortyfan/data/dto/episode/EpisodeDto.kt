package com.mukesh.rickmortyfan.data.dto.episode

import com.google.gson.annotations.SerializedName
import com.mukesh.rickmortyfan.domain.modal.episode.Episode

data class EpisodeDto(
    @SerializedName("air_date")
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)

fun EpisodeDto.toEpisode(): Episode {
    return Episode(
        airDate = this.airDate,
        characters = this.characters,
        created = this.created,
        episode = this.episode,
        id = this.id,
        name = this.name,
        url = this.url
    )
}