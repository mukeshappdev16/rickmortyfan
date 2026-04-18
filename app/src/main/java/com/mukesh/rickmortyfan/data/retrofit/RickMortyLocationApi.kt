package com.mukesh.rickmortyfan.data.retrofit

import com.mukesh.rickmortyfan.data.dto.location.LocationDto
import com.mukesh.rickmortyfan.data.dto.location.LocationListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickMortyLocationApi {

    @GET("location")
    suspend fun getAllLocations(
        @Query("page") page: Int
    ): LocationListResponse

    @GET("location/{locationId}")
    suspend fun getLocationDetail(@Path("locationId") locationId: String): LocationDto
}
