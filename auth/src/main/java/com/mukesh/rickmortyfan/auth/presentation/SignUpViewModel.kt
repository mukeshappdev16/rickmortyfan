package com.mukesh.rickmortyfan.auth.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.auth.domain.use_cases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
    @Inject
    constructor(
        private val signUpUseCase: SignUpUseCase,
    ) : ViewModel() {
        private val _state = mutableStateOf(SignUpState())
        val state: State<SignUpState> = _state

        fun signUp(
            email: String,
            password: String,
        ) {
            if (email.isEmpty() || email.isBlank()) {
                _state.value = SignUpState(error = "Email cannot be empty")
                return
            }
            if (password.isEmpty() || password.isBlank()) {
                _state.value = SignUpState(error = "Password cannot be empty")
                return
            }
            signUpUseCase(email, password).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = SignUpState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        _state.value =
                            SignUpState(error = result.message ?: "An unknown error occurred")
                    }

                    is Resource.Loading -> {
                        _state.value = SignUpState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
