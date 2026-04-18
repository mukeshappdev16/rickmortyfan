package com.mukesh.rickmortyfan.domain.repository

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.character.Characters

interface CharactersRepository {
    suspend fun getAllCharacters(page: Int): Characters
    suspend fun getCharacterDetail(charId: String): CharacterDescription
    suspend fun getMultipleCharacters(list: List<String>): List<CharacterDescription>
}