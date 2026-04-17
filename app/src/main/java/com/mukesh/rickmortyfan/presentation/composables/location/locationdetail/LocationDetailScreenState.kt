package com.mukesh.rickmortyfan.presentation.composables.location.locationdetail

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail

data class LocationDetailScreenState(
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val noInternet: Boolean = false,
    val location: LocationDetail? = null,
    val residents: List<CharacterDescription> = emptyList()
)
