package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.dto.character.CharacterDto
import com.mukesh.rickmortyfan.data.dto.character.CharacterListResponse
import com.mukesh.rickmortyfan.data.dto.character.InfoDto
import com.mukesh.rickmortyfan.data.retrofit.RickMortyCharacterApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharactersRepositoryImplTest {

    private lateinit var repository: CharactersRepositoryImpl
    private val api: RickMortyCharacterApi = mockk()

    @Before
    fun setUp() {
        repository = CharactersRepositoryImpl(api)
    }

    @Test
    fun `getAllCharacters should return characters from api`() = runTest {
        // Given
        val response = CharacterListResponse(
            info = InfoDto(count = 1, pages = 1, next = "", prev = null),
            results = listOf(mockk<CharacterDto>(relaxed = true))
        )
        coEvery { api.getAllCharacters(1) } returns response

        // When
        val result = repository.getAllCharacters(1)

        // Then
        assertEquals(response.results.size, result.charactersList.size)
    }

    @Test
    fun `getCharacterDetail should return character detail from api`() = runTest {
        // Given
        val characterDto = mockk<CharacterDto>(relaxed = true)
        coEvery { api.getCharacterDetail("1") } returns characterDto

        // When
        val result = repository.getCharacterDetail("1")

        // Then
        assertEquals(characterDto.name, result.name)
    }
}
