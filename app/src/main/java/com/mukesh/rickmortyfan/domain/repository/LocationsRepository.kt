package com.mukesh.rickmortyfan.domain.repository

import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import com.mukesh.rickmortyfan.domain.modal.location.Locations

interface LocationsRepository {
    suspend fun getAllLocations(page: Int): Locations

    suspend fun getLocationDetail(locationId: String): LocationDetail
}
