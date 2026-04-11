package com.mukesh.rickmortyfan.auth.domain.use_cases

import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.auth.domain.modal.RickMortyUser
import com.mukesh.rickmortyfan.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<RickMortyUser>> {
        return repository.login(email, password)
    }
}
