package com.mukesh.rickmortyfan.auth.data.dto

import com.google.firebase.auth.FirebaseUser
import com.mukesh.rickmortyfan.auth.domain.modal.RickMortyUser

fun FirebaseUser.toRickMortyUser(): RickMortyUser {
    return RickMortyUser(
        name = if (this.displayName?.isNotEmpty() == true) {
            this.displayName
        } else {
            this.email?.substringBefore("@") ?: "User"
        },
        email = this.email ?: "",
        profileImageUrl = this.photoUrl?.toString(),
        id = this.uid
    )
}
