package com.mukesh.rickmortyfan.data.repository

import com.mukesh.rickmortyfan.data.database.FavoritesDao
import com.mukesh.rickmortyfan.data.database.toCharacterDescription
import com.mukesh.rickmortyfan.data.database.toCharacterEntity
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.repository.FavoriteCharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteCharacterRepositoryImpl @Inject constructor(val favoritesDao: FavoritesDao) :
    FavoriteCharacterRepository {
    override fun getFavoriteCharacters(): Flow<List<CharacterDescription>> = flow {
        favoritesDao.getFavoriteCharacters().forEach { it.toCharacterDescription() }
    }

    override suspend fun addFavoriteCharacter(character: CharacterDescription): Long {
        return favoritesDao.addFavoriteCharacter(character.toCharacterEntity())
    }

    override suspend fun removeFavoriteCharacter(character: CharacterDescription): Int {
        return favoritesDao.removeFavoriteCharacter(character.toCharacterEntity())
    }

    override suspend fun isFavoriteCharacterPresent(id: Int): Boolean {
        return favoritesDao.isFavoriteCharacterPresent(id)
    }
}