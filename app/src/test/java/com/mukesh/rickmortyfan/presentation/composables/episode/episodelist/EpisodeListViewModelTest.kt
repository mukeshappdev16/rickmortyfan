package com.mukesh.rickmortyfan.presentation.composables.episode.episodelist

import com.mukesh.common.NetworkManager
import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.domain.modal.episode.Episodes
import com.mukesh.rickmortyfan.domain.modal.episode.Info
import com.mukesh.rickmortyfan.domain.use_cases.episodes.GetEpisodeListUseCase
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
class EpisodeListViewModelTest {
    private lateinit var viewModel: EpisodeListViewModel
    private val networkManager: NetworkManager = mockk()
    private val getEpisodeListUseCase: GetEpisodeListUseCase = mockk()
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
    fun `getEpisodes should update state with success when network is available`() =
        runTest {
            // Given
            val episodes =
                Episodes(
                    info = Info(count = 1, pages = 1, next = "", prev = null),
                    episodes = listOf(mockk<Episode>(relaxed = true)),
                )
            every { networkManager.isNetworkAvailable() } returns true
            every { getEpisodeListUseCase(1) } returns flowOf(Resource.Success(episodes))

            // When
            viewModel = EpisodeListViewModel(networkManager, getEpisodeListUseCase)
            advanceUntilIdle()

            // Then
            val state = viewModel.episodeListState.value
            assertEquals(episodes.episodes, state.list)
            assertEquals(false, state.isLoading)
            assertEquals(false, state.noInternet)
        }

    @Test
    fun `getEpisodes should update state with noInternet when network is unavailable`() =
        runTest {
            // Given
            every { networkManager.isNetworkAvailable() } returns false

            // When
            viewModel = EpisodeListViewModel(networkManager, getEpisodeListUseCase)
            advanceUntilIdle()

            // Then
            val state = viewModel.episodeListState.value
            assertTrue(state.noInternet)
            assertEquals(false, state.isLoading)
        }

    @Test
    fun `getEpisodes should update state with error when use case returns error`() =
        runTest {
            // Given
            val errorMessage = "An error occurred"
            every { networkManager.isNetworkAvailable() } returns true
            every { getEpisodeListUseCase(1) } returns flowOf(Resource.Error(errorMessage))

            // When
            viewModel = EpisodeListViewModel(networkManager, getEpisodeListUseCase)
            advanceUntilIdle()

            // Then
            val state = viewModel.episodeListState.value
            assertEquals(errorMessage, state.errorMessage)
            assertEquals(false, state.isLoading)
        }
}
