package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.dto.location.toLocationDetail
import com.mukesh.rickmortyfan.data.dto.location.toLocations
import com.mukesh.rickmortyfan.data.retrofit.RickMortyLocationApi
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import com.mukesh.rickmortyfan.domain.modal.location.Locations
import com.mukesh.rickmortyfan.domain.repository.LocationsRepository
import javax.inject.Inject

class LocationsRepositoryImpl
    @Inject
    constructor(
        private val locationApi: RickMortyLocationApi,
    ) : LocationsRepository {
        override suspend fun getAllLocations(page: Int): Locations {
            return locationApi.getAllLocations(page).toLocations()
        }

        override suspend fun getLocationDetail(locationId: String): LocationDetail {
            return locationApi.getLocationDetail(locationId).toLocationDetail()
        }
    }
