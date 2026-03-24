package com.mukesh.rickmortyfan.data.retrofit

import com.mukesh.rickmortyfan.data.dto.character.CharacterListResponse
import retrofit2.http.GET

interface RickMortyApi {

    @GET("character")
    suspend fun getCharacterList(): CharacterListResponse

    @GET("/character/2")
    suspend fun getCharacterDetail(charId: String)
}