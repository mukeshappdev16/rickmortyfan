package com.mukesh.rickmortyfan.data.dto.character

import com.mukesh.rickmortyfan.domain.modal.character.Characters

data class CharacterListResponse(
    val info: InfoDto,
    val results: List<CharacterDto>,
)

fun CharacterListResponse.toCharacters(): Characters {
    return Characters(
        info = this.info.toInfo(),
        charactersList = this.results.map { it.toCharacterDescription() },
    )
}
