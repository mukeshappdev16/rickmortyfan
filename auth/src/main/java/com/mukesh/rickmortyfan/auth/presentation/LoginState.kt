package com.mukesh.rickmortyfan.auth.presentation

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)
