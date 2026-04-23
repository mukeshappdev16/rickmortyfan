package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.dto.location.InfoDto
import com.mukesh.rickmortyfan.data.dto.location.LocationDto
import com.mukesh.rickmortyfan.data.dto.location.LocationListResponse
import com.mukesh.rickmortyfan.data.retrofit.RickMortyLocationApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocationsRepositoryImplTest {
    private lateinit var repository: LocationsRepositoryImpl
    private val api: RickMortyLocationApi = mockk()

    @Before
    fun setUp() {
        repository = LocationsRepositoryImpl(api)
    }

    @Test
    fun `getAllLocations should return locations from api`() =
        runTest {
            val response =
                LocationListResponse(
                    info = InfoDto(count = 1, pages = 1, next = "", prev = null),
                    results = listOf(mockk<LocationDto>(relaxed = true)),
                )
            coEvery { api.getAllLocations(1) } returns response

            val result = repository.getAllLocations(1)

            assertEquals(response.results.size, result.locations.size)
        }

    @Test
    fun `getLocationDetail should return location detail from api`() =
        runTest {
            val locationDto = mockk<LocationDto>(relaxed = true)
            coEvery { api.getLocationDetail("1") } returns locationDto

            val result = repository.getLocationDetail("1")

            assertEquals(locationDto.name, result.name)
        }
}
