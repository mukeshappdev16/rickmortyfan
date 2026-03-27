package com.mukesh.rickmortyfan.presentation.composables.location.locationlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.rickmortyfan.common.NetworkManager
import com.mukesh.rickmortyfan.common.Resource
import com.mukesh.rickmortyfan.domain.use_cases.locations.GetLocationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LocationListViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val getLocationListUseCase: GetLocationListUseCase
) : ViewModel() {

    private val _locationListState: MutableState<LocationListState> =
        mutableStateOf(LocationListState())
    val locationListState: State<LocationListState> = _locationListState

    init {
        getLocations()
    }

    fun getLocations() {
        if (!networkManager.isNetworkAvailable()) {
            _locationListState.value = LocationListState(noInternet = true)
            return
        }
        getLocationListUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _locationListState.value = LocationListState(isLoading = true)
                }

                is Resource.Success -> {
                    _locationListState.value = LocationListState(
                        list = result.data?.locations ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _locationListState.value = LocationListState(
                        errorMessage = result.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
