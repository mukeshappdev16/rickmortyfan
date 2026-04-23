package com.mukesh.rickmortyfan.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mukesh.rickmortyfan.data.database.converters.ListOfStringConverter
import com.mukesh.rickmortyfan.data.database.converters.LocationConverter
import com.mukesh.rickmortyfan.data.database.converters.OriginConverter
import com.mukesh.rickmortyfan.data.database.entity.CharacterEntity
import com.mukesh.rickmortyfan.data.database.entity.EpisodeEntity
import com.mukesh.rickmortyfan.data.database.entity.FavoritesDao
import com.mukesh.rickmortyfan.data.database.entity.LocationEntity

@Database(
    entities = [CharacterEntity::class, LocationEntity::class, EpisodeEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(ListOfStringConverter::class, LocationConverter::class, OriginConverter::class)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao

    override fun clearAllTables() {
        this.clearAllTables()
    }
}
