package com.mukesh.rickmortyfan.auth.domain.repository

import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.auth.domain.modal.RickMortyUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Resource<RickMortyUser>>
    fun signUp(email: String, password: String): Flow<Resource<RickMortyUser>>

    suspend fun getLoggedInUser(): Flow<RickMortyUser?>

    suspend fun logout()
}
