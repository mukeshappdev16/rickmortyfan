package com.mukesh.rickmortyfan.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail

@Entity(tableName = "favorite_locations")
data class LocationEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val url: String,
    val created: String
)

fun LocationEntity.toLocationDetail(): LocationDetail {
    return LocationDetail(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = emptyList(),
        url = url,
        created = created
    )
}

fun LocationDetail.toLocationEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        url = url,
        created = created
    )
}