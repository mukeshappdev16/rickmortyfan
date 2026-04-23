package com.mukesh.rickmortyfan.presentation.composables.character.characterdetail

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.episode.Episode

data class CharacterDetailScreenState(
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val noInternet: Boolean = false,
    val characterDescription: CharacterDescription? = null,
    val episodes: List<Episode> = emptyList(),
)
