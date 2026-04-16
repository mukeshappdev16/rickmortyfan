package com.mukesh.rickmortyfan.domain.modal.character

import kotlinx.serialization.Serializable

data class Characters(
    val info: Info,
    val charactersList: List<CharacterDescription>
)

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String?
)

data class CharacterDescription(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

@Serializable
data class Location(
    val name: String,
    val url: String
)

@Serializable
data class Origin(
    val name: String,
    val url: String
)
