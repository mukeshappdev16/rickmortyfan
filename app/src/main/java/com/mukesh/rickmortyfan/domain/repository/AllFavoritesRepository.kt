package com.mukesh.rickmortyfan.domain.repository

interface AllFavoritesRepository {
    suspend fun clearAllFavorites()
}