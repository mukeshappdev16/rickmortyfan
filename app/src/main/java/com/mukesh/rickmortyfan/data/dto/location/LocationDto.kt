package com.mukesh.rickmortyfan.data.dto.location

import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail

data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)

fun LocationDto.toLocationDetail(): LocationDetail {
    return LocationDetail(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents,
        url = url,
        created = created
    )
}
