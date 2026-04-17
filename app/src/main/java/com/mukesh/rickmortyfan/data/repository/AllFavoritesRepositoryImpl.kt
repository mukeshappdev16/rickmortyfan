package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.database.FavoritesDatabase
import com.mukesh.rickmortyfan.domain.repository.AllFavoritesRepository

class AllFavoritesRepositoryImpl(private val favoritesDatabase: FavoritesDatabase) :
    AllFavoritesRepository {
    override suspend fun clearAllFavorites() {
        favoritesDatabase.clearAllTables()
    }
}