package com.mukesh.rickmortyfan.auth.presentation

data class SignUpState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
)
