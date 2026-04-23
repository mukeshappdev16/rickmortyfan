package com.mukesh.rickmortyfan.presentation.composables.character.characterList

import com.mukesh.common.NetworkManager
import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.character.Characters
import com.mukesh.rickmortyfan.domain.modal.character.Info
import com.mukesh.rickmortyfan.domain.use_cases.characters.GetCharacterListUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {
    private lateinit var viewModel: CharacterListViewModel
    private val networkManager: NetworkManager = mockk()
    private val getCharacterListUseCase: GetCharacterListUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCharacters should update state with success when network is available`() =
        runTest {
            // Given
            val characters =
                Characters(
                    info = Info(count = 1, pages = 1, next = "null", prev = null),
                    charactersList = listOf(mockk<CharacterDescription>()),
                )
            every { networkManager.isNetworkAvailable() } returns true
            every { getCharacterListUseCase(1) } returns flowOf(Resource.Success(characters))

            // When
            viewModel = CharacterListViewModel(networkManager, getCharacterListUseCase)
            advanceUntilIdle()

            // Then
            val state = viewModel.characterListState.value
            assertEquals(characters.charactersList, state.list)
            assertEquals(false, state.isLoading)
            assertEquals(false, state.noInternet)
        }

    @Test
    fun `getCharacters should update state with noInternet when network is unavailable`() =
        runTest {
            // Given
            every { networkManager.isNetworkAvailable() } returns false

            // When
            viewModel = CharacterListViewModel(networkManager, getCharacterListUseCase)
            advanceUntilIdle()

            // Then
            val state = viewModel.characterListState.value
            assertTrue(state.noInternet)
            assertEquals(false, state.isLoading)
        }

    @Test
    fun `getCharacters should update state with error when use case returns error`() =
        runTest {
            // Given
            val errorMessage = "An error occurred"
            every { networkManager.isNetworkAvailable() } returns true
            every { getCharacterListUseCase(1) } returns flowOf(Resource.Error(errorMessage))

            // When
            viewModel = CharacterListViewModel(networkManager, getCharacterListUseCase)
            advanceUntilIdle()

            // Then
            val state = viewModel.characterListState.value
            assertEquals(errorMessage, state.errorMessage)
            assertEquals(false, state.isLoading)
        }
}
