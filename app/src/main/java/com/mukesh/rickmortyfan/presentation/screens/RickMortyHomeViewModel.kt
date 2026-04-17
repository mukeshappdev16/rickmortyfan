package com.mukesh.rickmortyfan.presentation.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.rickmortyfan.auth.domain.modal.RickMortyUser
import com.mukesh.rickmortyfan.auth.domain.use_cases.GetLoggedInUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickMortyHomeViewModel @Inject constructor(
    private val getLoggedInUserInfoUseCase: GetLoggedInUserInfoUseCase
) : ViewModel() {

    private val _user: MutableState<RickMortyUser?> = mutableStateOf(null)
    val user: State<RickMortyUser?> = _user

    init {
        viewModelScope.launch {
            getLoggedInUserInfoUseCase().collect {
                _user.value = it
            }
        }
    }
}