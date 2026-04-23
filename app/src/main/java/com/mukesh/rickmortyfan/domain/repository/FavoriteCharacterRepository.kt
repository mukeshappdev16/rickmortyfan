package com.mukesh.rickmortyfan.domain.repository

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import kotlinx.coroutines.flow.Flow

interface FavoriteCharacterRepository {
    fun getFavoriteCharacters(): Flow<List<CharacterDescription>>

    suspend fun addFavoriteCharacter(character: CharacterDescription): Long

    suspend fun removeFavoriteCharacter(character: CharacterDescription): Int

    suspend fun isFavoriteCharacterPresent(id: Int): Boolean
}
