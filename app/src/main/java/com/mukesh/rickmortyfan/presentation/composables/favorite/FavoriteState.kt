package com.mukesh.rickmortyfan.presentation.composables.favorite

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail

data class FavoriteState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val characterList: List<CharacterDescription> = emptyList(),
    val locationList: List<LocationDetail> = emptyList(),
    val episodeList: List<Episode> = emptyList(),
)
