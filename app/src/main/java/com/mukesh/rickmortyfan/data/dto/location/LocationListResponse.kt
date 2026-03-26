package com.mukesh.rickmortyfan.data.dto.location


import com.mukesh.rickmortyfan.domain.modal.location.Locations

data class LocationListResponse(
    val info: InfoDto,
    val results: List<LocationDto>
)

fun LocationListResponse.toLocations(): Locations {
    return Locations(
        info = this.info.toInfo(),
        locations = this.results.map { it.toLocationDetail() }
    )
}
