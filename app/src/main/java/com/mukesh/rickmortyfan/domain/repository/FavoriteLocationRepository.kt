package com.mukesh.rickmortyfan.domain.repository

import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import kotlinx.coroutines.flow.Flow

interface FavoriteLocationRepository {
    fun getFavoriteLocations(): Flow<List<LocationDetail>>
    suspend fun addFavoriteLocation(locationDetail: LocationDetail): Long
    suspend fun removeFavoriteLocation(locationDetail: LocationDetail): Int
    suspend fun isFavoriteLocationPresent(id: Int): Boolean
}