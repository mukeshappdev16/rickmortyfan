package com.mukesh.rickmortyfan.data.retrofit

import com.mukesh.rickmortyfan.data.dto.character.CharacterDto
import com.mukesh.rickmortyfan.data.dto.character.CharacterListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RickMortyCharacterApi {

    @GET("character")
    suspend fun getAllCharacters(): CharacterListResponse

    @GET("character/{charId}")
    suspend fun getCharacterDetail(@Path("charId") charId: String): CharacterDto

    @GET("character/{multipleCharId}")
    suspend fun getMultipleCharacters(@Path("multipleCharId") multipleCharId: String): List<CharacterDto>
}