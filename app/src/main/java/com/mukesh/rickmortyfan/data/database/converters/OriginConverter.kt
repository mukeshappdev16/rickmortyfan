package com.mukesh.rickmortyfan.data.database.converters

import androidx.room.TypeConverter
import com.mukesh.rickmortyfan.domain.modal.character.Origin
import kotlinx.serialization.json.Json

class OriginConverter {
    @TypeConverter
    fun fromOrigin(origin: Origin): String {
        return Json.encodeToString(origin)
    }

    @TypeConverter
    fun toOrigin(addressString: String): Origin {
        return Json.decodeFromString(addressString)
    }
}
