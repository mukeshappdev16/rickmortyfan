package com.mukesh.rickmortyfan.presentation.composables.location.locationlist

import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail

data class LocationListState(
    val isLoading: Boolean = false,
    val isPaginating: Boolean = false,
    val errorMessage: String = "",
    val noInternet: Boolean = false,
    val list: List<LocationDetail> = emptyList(),
    val page: Int = 1,
    val endReached: Boolean = false,
)
