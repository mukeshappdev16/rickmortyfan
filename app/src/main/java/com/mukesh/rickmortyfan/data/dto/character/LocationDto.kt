package com.mukesh.rickmortyfan.data.dto.character

import com.mukesh.rickmortyfan.domain.modal.character.Location

data class LocationDto(
    val name: String,
    val url: String
)

fun LocationDto.toLocation(): Location {
    return Location(name = this.name, url = this.url)
}