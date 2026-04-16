package com.mukesh.rickmortyfan.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mukesh.rickmortyfan.domain.modal.episode.Episode

@Entity(tableName = "favorite_episode")
data class EpisodeEntity(
    @PrimaryKey val id: Int,
    val air_date: String,
    val created: String,
    val episode: String,
    val name: String,
    val url: String
)

fun EpisodeEntity.toEpisode(): Episode {
    return Episode(
        air_date = air_date,
        characters = emptyList(),
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url
    )
}

fun Episode.toEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(
        air_date = air_date,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url
    )
}