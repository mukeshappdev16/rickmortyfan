package com.mukesh.rickmortyfan.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mukesh.rickmortyfan.domain.modal.episode.Episode

@Entity(tableName = "favorite_episode")
data class EpisodeEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("air_date")
    val airDate: String,
    val created: String,
    val episode: String,
    val name: String,
    val url: String,
    val characters: List<String>
)

fun EpisodeEntity.toEpisode(): Episode {
    return Episode(
        airDate = airDate,
        characters = characters,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url
    )
}

fun Episode.toEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(
        airDate = airDate,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url,
        characters = characters
    )
}