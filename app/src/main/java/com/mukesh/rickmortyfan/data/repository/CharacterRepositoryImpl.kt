package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.dto.character.toCharacters
import com.mukesh.rickmortyfan.data.retrofit.RickMortyApi
import com.mukesh.rickmortyfan.domain.modal.character.Characters
import com.mukesh.rickmortyfan.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(val rickMortyApi: RickMortyApi) :
    CharacterRepository {
    override suspend fun getCharacters(): Characters {
        return rickMortyApi.getCharacterList().toCharacters()
    }

    override suspend fun getCharacterDetail(charId: String) {
        rickMortyApi.getCharacterDetail(charId)
    }
}