package com.mukesh.rickmortyfan.auth.domain.modal

data class RickMortyUser(
    val name: String,
    val email: String,
    val profileImageUrl: String?,
    val id: String
)
