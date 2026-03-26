package com.mukesh.rickmortyfan.data.retrofit

import com.mukesh.rickmortyfan.data.dto.episode.EpisodeDto
import com.mukesh.rickmortyfan.data.dto.episode.EpisodesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RickMortyEpisodesApi {
    @GET("episode")
    suspend fun getAllEpisodes(): EpisodesResponse

    @GET("episode/{episodeId}")
    suspend fun getEpisodeDetail(@Path("episodeId") episodeId: String): EpisodeDto

    @GET("episode/{multipleEpisodesId}")
    suspend fun getMultipleEpisodes(@Path("multipleEpisodesId") multipleEpisodesId: String): List<EpisodeDto>
}