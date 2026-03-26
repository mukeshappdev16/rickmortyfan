package com.mukesh.rickmortyfan.presentation.composables.location.locationlist

import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail

data class LocationListState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val list: List<LocationDetail> = emptyList()
)
