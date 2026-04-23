package com.mukesh.rickmortyfan.data.database.converters

import androidx.room.TypeConverter
import com.mukesh.rickmortyfan.domain.modal.character.Location
import kotlinx.serialization.json.Json

class LocationConverter {
    @TypeConverter
    fun fromLocation(address: Location): String {
        return Json.encodeToString(address)
    }

    @TypeConverter
    fun toLocation(addressString: String): Location {
        return Json.decodeFromString(addressString)
    }
}
