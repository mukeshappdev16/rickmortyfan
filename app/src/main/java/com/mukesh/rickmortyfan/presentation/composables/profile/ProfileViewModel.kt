package com.mukesh.rickmortyfan.presentation.composables.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.rickmortyfan.auth.domain.use_cases.GetLoggedInUserInfoUseCase
import com.mukesh.rickmortyfan.auth.domain.use_cases.LogoutUseCase
import com.mukesh.rickmortyfan.domain.use_cases.favorite.ClearAllFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getLoggedInUserInfoUseCase: GetLoggedInUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val clearAllFavoritesUseCase: ClearAllFavoritesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        getProfileInfo()
    }

    private fun getProfileInfo() {
        viewModelScope.launch {
            getLoggedInUserInfoUseCase().onEach { user ->
                _state.value = _state.value.copy(user = user)
            }.launchIn(viewModelScope)
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase()
            clearAllFavoritesUseCase()
            _state.value = _state.value.copy(isLoggedOut = true)
        }
    }
}
