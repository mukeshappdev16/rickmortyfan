package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.dto.character.toCharacterDescription
import com.mukesh.rickmortyfan.data.dto.character.toCharacters
import com.mukesh.rickmortyfan.data.retrofit.RickMortyCharacterApi
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.character.Characters
import com.mukesh.rickmortyfan.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(val rickMortyCharacterApi: RickMortyCharacterApi) :
    CharactersRepository {
    override suspend fun getAllCharacters(): Characters {
        return rickMortyCharacterApi.getAllCharacters().toCharacters()
    }

    override suspend fun getCharacterDetail(charId: String): CharacterDescription {
        return rickMortyCharacterApi.getCharacterDetail(charId).toCharacterDescription()
    }

    override suspend fun getMultipleCharacters(list: List<String>): List<CharacterDescription> {

        return rickMortyCharacterApi.getMultipleCharacters(list.joinToString())
            .map { it.toCharacterDescription() }
    }
}