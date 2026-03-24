package com.mukesh.rickmortyfan.domain.repository

import com.mukesh.rickmortyfan.domain.modal.character.Characters

interface CharacterRepository {
    suspend fun getCharacters(): Characters
    suspend fun getCharacterDetail(charId: String)
}