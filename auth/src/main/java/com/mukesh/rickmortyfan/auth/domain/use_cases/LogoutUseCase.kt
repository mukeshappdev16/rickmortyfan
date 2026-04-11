package com.mukesh.rickmortyfan.auth.domain.use_cases

import com.mukesh.rickmortyfan.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        return repository.logout()
    }
}
