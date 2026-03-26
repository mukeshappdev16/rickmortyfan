package com.mukesh.rickmortyfan.presentation.composables.character.characterdetail

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.episode.Episode

data class CharacterDetailScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val characterDescription: CharacterDescription? = null,
    val episodes: List<Episode> = emptyList()
)
