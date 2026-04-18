package com.mukesh.rickmortyfan.presentation.composables.location.locationlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.common.NetworkManager
import com.mukesh.common.Resource
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
        if (_locationListState.value.endReached || _locationListState.value.isPaginating) return

        if (!networkManager.isNetworkAvailable()) {
            if (_locationListState.value.list.isEmpty()) {
                _locationListState.value = _locationListState.value.copy(noInternet = true)
            }
            return
        }

        val isFirstPage = _locationListState.value.page == 1

        getLocationListUseCase(_locationListState.value.page).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    if (isFirstPage) {
                        _locationListState.value = _locationListState.value.copy(isLoading = true)
                    } else {
                        _locationListState.value = _locationListState.value.copy(isPaginating = true)
                    }
                }

                is Resource.Success -> {
                    val newList = result.data?.locations ?: emptyList()
                    _locationListState.value = _locationListState.value.copy(
                        isLoading = false,
                        isPaginating = false,
                        list = _locationListState.value.list + newList,
                        page = _locationListState.value.page + 1,
                        endReached = newList.isEmpty() || result.data?.info?.next == null,
                        noInternet = false,
                        errorMessage = ""
                    )
                }

                is Resource.Error -> {
                    _locationListState.value = _locationListState.value.copy(
                        isLoading = false,
                        isPaginating = false,
                        errorMessage = if (isFirstPage) result.message ?: "An unexpected error occurred" else ""
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
