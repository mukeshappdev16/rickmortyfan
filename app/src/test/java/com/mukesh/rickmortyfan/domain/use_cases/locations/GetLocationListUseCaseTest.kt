package com.mukesh.rickmortyfan.domain.use_cases.locations

import app.cash.turbine.test
import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.modal.location.Locations
import com.mukesh.rickmortyfan.domain.repository.LocationsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetLocationListUseCaseTest {

    private lateinit var getLocationListUseCase: GetLocationListUseCase
    private val repository: LocationsRepository = mockk()

    @Before
    fun setUp() {
        getLocationListUseCase = GetLocationListUseCase(repository)
    }

/*    @Test
    fun `invoke should emit Loading and then Success when repository returns data`() = runTest {
        val locations = mockk<Locations>()
        coEvery { repository.getAllLocations(1) } returns locations

        getLocationListUseCase(1).test {
            val firstItem = awaitItem()
            assertTrue("Expected Loading but was $firstItem", firstItem is Resource.Loading)
            
            val secondItem = awaitItem()
            assertTrue("Expected Success but was $secondItem", secondItem is Resource.Success)
            assertEquals(locations, secondItem.data)
            
            awaitComplete()
        }
    }

    @Test
    fun `invoke should emit Loading and then Error when repository throws exception`() = runTest {
        coEvery { repository.getAllLocations(1) } throws Exception("Error")

        getLocationListUseCase(1).test {
            val firstItem = awaitItem()
            assertTrue("Expected Loading but was $firstItem", firstItem is Resource.Loading)
            
            val secondItem = awaitItem()
            assertTrue("Expected Error but was $secondItem", secondItem is Resource.Error)
            assertEquals("Something went wrong. Please try again later", secondItem.message)

            awaitComplete()
        }
    }*/
}
