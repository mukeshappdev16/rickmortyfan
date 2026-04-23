package com.mukesh.rickmortyfan.presentation.composables.location.locationdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.common.NetworkManager
import com.mukesh.common.Resource
import com.mukesh.common.Utils
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import com.mukesh.rickmortyfan.domain.use_cases.characters.GetMultipleCharacterUseCase
import com.mukesh.rickmortyfan.domain.use_cases.favorite.locations.AddFavoriteLocationUseCase
import com.mukesh.rickmortyfan.domain.use_cases.favorite.locations.IsFavoriteLocationUseCase
import com.mukesh.rickmortyfan.domain.use_cases.favorite.locations.RemoveFavoriteLocationUseCase
import com.mukesh.rickmortyfan.domain.use_cases.locations.GetLocationDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel
    @Inject
    constructor(
        private val networkManager: NetworkManager,
        private val getLocationDetailUseCase: GetLocationDetailUseCase,
        private val getMultipleCharacterUseCase: GetMultipleCharacterUseCase,
        private val addFavoriteLocationUseCase: AddFavoriteLocationUseCase,
        private val removeFavoriteLocationUseCase: RemoveFavoriteLocationUseCase,
        private val isFavoriteLocationUseCase: IsFavoriteLocationUseCase,
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
                        isFavoriteLocation(result.data?.id ?: 0)
                    }

                    is Resource.Error -> {
                        _state.value =
                            LocationDetailScreenState(
                                errorMessage = result.message ?: "An unexpected error occurred",
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
                        _state.value =
                            _state.value.copy(
                                residents = result.data ?: emptyList(),
                            )
                    }

                    else -> { // Handle error or loading if needed
                    }
                }
            }.launchIn(viewModelScope)
        }

        fun addFavoriteLocation(locationDetail: LocationDetail) {
            viewModelScope.launch {
                val isAdded = addFavoriteLocationUseCase(locationDetail)
                if (isAdded > 0) {
                    _state.value =
                        _state.value.copy(isFavorite = true)
                }
            }
        }

        fun removeFavoriteLocation(locationDetail: LocationDetail) {
            viewModelScope.launch {
                val isRemoved = removeFavoriteLocationUseCase(locationDetail)
                if (isRemoved == 1) {
                    _state.value =
                        _state.value.copy(isFavorite = false)
                }
            }
        }

        fun isFavoriteLocation(locationId: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                val isFavorite = isFavoriteLocationUseCase(locationId)
                _state.value = _state.value.copy(isFavorite = isFavorite)
            }
        }
    }
