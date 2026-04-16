package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.database.FavoritesDao
import com.mukesh.rickmortyfan.data.database.toLocationDetail
import com.mukesh.rickmortyfan.data.database.toLocationEntity
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import com.mukesh.rickmortyfan.domain.repository.FavoriteLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteLocationRepositoryImpl @Inject constructor(val favoritesDao: FavoritesDao) :
    FavoriteLocationRepository {
    override fun getFavoriteLocations(): Flow<List<LocationDetail>> = flow {
        favoritesDao.getFavoriteLocations().forEach { it.toLocationDetail() }
    }

    override suspend fun addFavoriteLocation(locationDetail: LocationDetail): Long {
        return favoritesDao.addFavoriteLocation(locationDetail.toLocationEntity())
    }

    override suspend fun removeFavoriteLocation(locationDetail: LocationDetail): Int {
        return favoritesDao.removeFavoriteLocation(locationDetail.toLocationEntity())
    }

    override suspend fun isFavoriteLocationPresent(id: Int): Boolean {
        return favoritesDao.isFavoriteLocationPresent(id)
    }
}