package com.mukesh.rickmortyfan.data.dto.character

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription

data class CharacterDto(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LocationDto,
    val name: String,
    val origin: OriginDto,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

fun CharacterDto.toCharacterDescription(): CharacterDescription {
    return CharacterDescription(
        created = this.created,
        episode = this.episode,
        gender = this.gender,
        id = this.id,
        image = this.image,
        location = this.location.toLocation(),
        name = this.name,
        origin = this.origin.toOrigin(),
        species = this.species,
        status = this.status,
        type = this.type,
        url = this.url
    )
}