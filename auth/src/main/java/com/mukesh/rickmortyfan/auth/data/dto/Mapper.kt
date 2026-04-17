package com.mukesh.rickmortyfan.auth.data.dto

import com.google.firebase.auth.FirebaseUser
import com.mukesh.rickmortyfan.auth.domain.modal.RickMortyUser

fun FirebaseUser.toRickMortyUser(): RickMortyUser {
    return RickMortyUser(
        name = this.displayName ?: this.email?.substringBefore("@") ?: "User",
        email = this.email ?: "",
        profileImageUrl = this.photoUrl?.toString(),
        id = this.uid
    )
}
