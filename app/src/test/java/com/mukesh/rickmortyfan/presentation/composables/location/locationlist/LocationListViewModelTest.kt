package com.mukesh.rickmortyfan.presentation.composables.location.locationlist

import com.mukesh.common.NetworkManager
import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import com.mukesh.rickmortyfan.domain.modal.location.Locations
import com.mukesh.rickmortyfan.domain.modal.location.Info
import com.mukesh.rickmortyfan.domain.use_cases.locations.GetLocationListUseCase
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
class LocationListViewModelTest {

    private lateinit var viewModel: LocationListViewModel
    private val networkManager: NetworkManager = mockk()
    private val getLocationListUseCase: GetLocationListUseCase = mockk()
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
    fun `getLocations should update state with success when network is available`() = runTest {
        // Given
        val locations = Locations(
            info = Info(count = 1, pages = 1, next = "", prev = null),
            locations = listOf(mockk<LocationDetail>(relaxed = true))
        )
        every { networkManager.isNetworkAvailable() } returns true
        every { getLocationListUseCase(1) } returns flowOf(Resource.Success(locations))

        // When
        viewModel = LocationListViewModel(networkManager, getLocationListUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.locationListState.value
        assertEquals(locations.locations, state.list)
        assertEquals(false, state.isLoading)
        assertEquals(false, state.noInternet)
    }

    @Test
    fun `getLocations should update state with noInternet when network is unavailable`() = runTest {
        // Given
        every { networkManager.isNetworkAvailable() } returns false

        // When
        viewModel = LocationListViewModel(networkManager, getLocationListUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.locationListState.value
        assertTrue(state.noInternet)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `getLocations should update state with error when use case returns error`() = runTest {
        // Given
        val errorMessage = "An error occurred"
        every { networkManager.isNetworkAvailable() } returns true
        every { getLocationListUseCase(1) } returns flowOf(Resource.Error(errorMessage))

        // When
        viewModel = LocationListViewModel(networkManager, getLocationListUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.locationListState.value
        assertEquals(errorMessage, state.errorMessage)
        assertEquals(false, state.isLoading)
    }
}
