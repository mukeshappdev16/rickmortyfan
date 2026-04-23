package com.mukesh.rickmortyfan.data.dto.character

import com.mukesh.rickmortyfan.domain.modal.character.Info

data class InfoDto(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String?,
)

fun InfoDto.toInfo(): Info {
    return Info(count = this.count, next = this.next, pages = this.pages, prev = this.prev)
}
