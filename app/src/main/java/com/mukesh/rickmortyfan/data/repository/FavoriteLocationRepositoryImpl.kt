package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.database.entity.FavoritesDao
import com.mukesh.rickmortyfan.data.database.entity.toLocationDetail
import com.mukesh.rickmortyfan.data.database.entity.toLocationEntity
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import com.mukesh.rickmortyfan.domain.repository.FavoriteLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteLocationRepositoryImpl @Inject constructor(val favoritesDao: FavoritesDao) :
    FavoriteLocationRepository {
    override fun getFavoriteLocations(): Flow<List<LocationDetail>> {
        return favoritesDao.getFavoriteLocations().map { entities ->
            entities.map { it.toLocationDetail() }
        }
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