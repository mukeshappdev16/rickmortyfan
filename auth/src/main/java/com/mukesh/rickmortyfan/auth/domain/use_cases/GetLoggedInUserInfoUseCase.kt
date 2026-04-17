package com.mukesh.rickmortyfan.auth.domain.use_cases

import com.mukesh.rickmortyfan.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetLoggedInUserInfoUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.getLoggedInUser()
}