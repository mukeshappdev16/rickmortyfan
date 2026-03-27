package com.mukesh.rickmortyfan.presentation.composables.location.locationdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.rickmortyfan.common.NetworkManager
import com.mukesh.rickmortyfan.common.Resource
import com.mukesh.rickmortyfan.common.Utils
import com.mukesh.rickmortyfan.domain.use_cases.characters.GetMultipleCharacterUseCase
import com.mukesh.rickmortyfan.domain.use_cases.locations.GetLocationDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val getLocationDetailUseCase: GetLocationDetailUseCase,
    private val getMultipleCharacterUseCase: GetMultipleCharacterUseCase
) : ViewModel() {

    private val _state: MutableState<LocationDetailScreenState> =
        mutableStateOf(LocationDetailScreenState())
    val state: State<LocationDetailScreenState> = _state

    fun getLocationDetail(locationId: String) {
        if (!networkManager.isNetworkAvailable()) {
            _state.value = LocationDetailScreenState(noInternet = true)
            return
        }
        getLocationDetailUseCase(locationId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = LocationDetailScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = LocationDetailScreenState(location = result.data)
                    result.data?.residents?.let { residents ->
                        getResidents(Utils.getIdsFromUrlList(residents))
                    }
                }
                is Resource.Error -> {
                    _state.value = LocationDetailScreenState(
                        errorMessage = result.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getResidents(residentIds: List<String>) {
        if (residentIds.isEmpty()) return
        
        getMultipleCharacterUseCase(residentIds).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        residents = result.data ?: emptyList()
                    )
                }
                else -> { /* Handle error or loading if needed */ }
            }
        }.launchIn(viewModelScope)
    }
}
