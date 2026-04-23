package com.mukesh.rickmortyfan.domain.use_cases.characters

import com.mukesh.rickmortyfan.domain.repository.CharactersRepository
import io.mockk.mockk
import org.junit.Before

class GetCharacterListUseCaseTest {
    private lateinit var getCharacterListUseCase: GetCharacterListUseCase
    private val repository: CharactersRepository = mockk()

    @Before
    fun setUp() {
        getCharacterListUseCase = GetCharacterListUseCase(repository)
    }

   /* @Test
    fun `invoke should emit Loading and then Success when repository returns data`() = runTest {
        // Given
        val characters = mockk<Characters>()
        coEvery { repository.getAllCharacters(1) } returns characters

        // When & Then
        getCharacterListUseCase(1).test {
            assertTrue(awaitItem() is Resource.Loading)
            val success = awaitItem()
            assertTrue(success is Resource.Success)
            assertEquals(characters, success.data)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should emit Loading and then Error when repository throws exception`() = runTest {
        // Given
        coEvery { repository.getAllCharacters(1) } throws Exception("Error")

        // When & Then
        getCharacterListUseCase(1).test {
            assertTrue(awaitItem() is Resource.Loading)
            val error = awaitItem()
            assertTrue(error is Resource.Error)
            assertEquals("Something went wrong. Please try again later", error.message)
            awaitComplete()
        }
    }*/
}
