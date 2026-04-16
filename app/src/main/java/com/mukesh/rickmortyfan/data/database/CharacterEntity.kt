package com.mukesh.rickmortyfan.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.character.Location
import com.mukesh.rickmortyfan.domain.modal.character.Origin

@Entity(tableName = "favorite_characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val created: String,
    val gender: String,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    val episode: List<String>,
    val origin: Origin,
    val location: Location
)

fun CharacterEntity.toCharacterDescription(): CharacterDescription {
    return CharacterDescription(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = null,
        image = image,
        episode = emptyList(),
        url = url,
        created = created,
        location = null
    )
}

fun CharacterDescription.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        image = image,
        url = url,
        created = created
    )
}

