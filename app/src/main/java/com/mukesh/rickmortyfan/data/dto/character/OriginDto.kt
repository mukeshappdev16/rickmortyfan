package com.mukesh.rickmortyfan.data.dto.character

import com.mukesh.rickmortyfan.domain.modal.character.Origin

data class OriginDto(
    val name: String,
    val url: String,
)

fun OriginDto.toOrigin(): Origin {
    return Origin(name = this.name, url = this.url)
}
