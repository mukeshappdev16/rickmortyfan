package com.mukesh.rickmortyfan.domain.repository

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.character.Characters
import retrofit2.http.GET

interface CharactersRepository {
    suspend fun getAllCharacters(): Characters
    suspend fun getCharacterDetail(charId: String): CharacterDescription
    @GET
    suspend fun getMultipleCharacters(list: List<String>): List<CharacterDescription>

}