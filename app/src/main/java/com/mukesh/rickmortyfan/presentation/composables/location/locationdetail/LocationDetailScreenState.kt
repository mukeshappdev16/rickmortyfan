package com.mukesh.rickmortyfan.presentation.composables.location.locationdetail

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail

data class LocationDetailScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val location: LocationDetail? = null,
    val residents: List<CharacterDescription> = emptyList()
)
