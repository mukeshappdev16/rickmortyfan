package com.mukesh.rickmortyfan.presentation.composables.profile

import com.mukesh.rickmortyfan.auth.domain.modal.RickMortyUser

data class ProfileState(
    val user: RickMortyUser? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedOut: Boolean = false,
)
