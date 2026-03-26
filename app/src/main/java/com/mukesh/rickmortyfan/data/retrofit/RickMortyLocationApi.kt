package com.mukesh.rickmortyfan.data.retrofit

import com.mukesh.rickmortyfan.data.dto.location.LocationDto
import com.mukesh.rickmortyfan.data.dto.location.LocationListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RickMortyLocationApi {

    @GET("location")
    suspend fun getAllLocations(): LocationListResponse

    @GET("location/{locationId}")
    suspend fun getLocationDetail(@Path("locationId") locationId: String): LocationDto
}
