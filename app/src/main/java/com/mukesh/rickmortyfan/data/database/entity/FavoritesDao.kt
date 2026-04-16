package com.mukesh.rickmortyfan.data.database.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorite_characters")
    suspend fun getFavoriteCharacters(): List<CharacterEntity>

    @Insert(onConflict = REPLACE)
    suspend fun addFavoriteCharacter(character: CharacterEntity): Long

    @Delete
    suspend fun removeFavoriteCharacter(character: CharacterEntity): Int

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE id = :id)")
    fun isFavoriteCharacterPresent(id: Int): Boolean

    @Query("SELECT * FROM favorite_locations")
    suspend fun getFavoriteLocations(): List<LocationEntity>

    @Insert(onConflict = REPLACE)
    suspend fun addFavoriteLocation(location: LocationEntity): Long

    @Delete
    suspend fun removeFavoriteLocation(location: LocationEntity): Int

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_locations WHERE id = :id)")
    fun isFavoriteLocationPresent(id: Int): Boolean

    @Query("SELECT * FROM favorite_episode")
    suspend fun getFavoriteEpisodes(): List<EpisodeEntity>

    @Insert(onConflict = REPLACE)
    suspend fun addFavoriteEpisode(episode: EpisodeEntity): Long

    @Delete
    suspend fun removeFavoriteEpisode(episode: EpisodeEntity): Int

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_episode WHERE id = :id)")
    fun isFavoriteEpisodePresent(id: Int): Boolean

}