package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.dto.episode.EpisodeDto
import com.mukesh.rickmortyfan.data.dto.episode.EpisodesResponse
import com.mukesh.rickmortyfan.data.dto.episode.InfoDto
import com.mukesh.rickmortyfan.data.retrofit.RickMortyEpisodesApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EpisodesRepositoryImplTest {

    private lateinit var repository: EpisodesRepositoryImpl
    private val api: RickMortyEpisodesApi = mockk()

    @Before
    fun setUp() {
        repository = EpisodesRepositoryImpl(api)
    }

    @Test
    fun `getAllEpisodes should return episodes from api`() = runTest {
        val response = EpisodesResponse(
            info = InfoDto(count = 1, pages = 1, next = "", prev = null),
            results = listOf(mockk<EpisodeDto>(relaxed = true))
        )
        coEvery { api.getAllEpisodes(1) } returns response

        val result = repository.getAllEpisodes(1)

        assertEquals(response.results.size, result.episodes.size)
    }

    @Test
    fun `getEpisodeDetail should return episode detail from api`() = runTest {
        val episodeDto = mockk<EpisodeDto>(relaxed = true)
        coEvery { api.getEpisodeDetail("1") } returns episodeDto

        val result = repository.getEpisodeDetail("1")

        assertEquals(episodeDto.name, result.name)
    }
}
