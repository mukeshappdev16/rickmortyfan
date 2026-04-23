package com.mukesh.rickmortyfan.domain.use_cases.characters

import app.cash.turbine.test
import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.repository.CharactersRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterDetailUseCaseTest {
    private lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase
    private val repository: CharactersRepository = mockk()

    @Before
    fun setUp() {
        getCharacterDetailUseCase = GetCharacterDetailUseCase(repository)
    }

    @Test
    fun `invoke should emit Loading and then Success when repository returns data`() =
        runTest {
            // Given
            val character = mockk<CharacterDescription>()
            coEvery { repository.getCharacterDetail("1") } returns character

            // When & Then
            getCharacterDetailUseCase("1").test {
                assertTrue(awaitItem() is Resource.Loading)
                val success = awaitItem()
                assertTrue(success is Resource.Success)
                assertEquals(character, success.data)
                awaitComplete()
            }
        }

    @Test
    fun `invoke should emit Loading and then Error when repository throws exception`() =
        runTest {
            // Given
            coEvery { repository.getCharacterDetail("1") } throws Exception("Error")

            // When & Then
            getCharacterDetailUseCase("1").test {
                assertTrue(awaitItem() is Resource.Loading)
                val error = awaitItem()
                assertTrue(error is Resource.Error)
                assertEquals("Something went wrong. Please try again later", error.message)
                awaitComplete()
            }
        }
}
