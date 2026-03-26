package com.mukesh.rickmortyfan.presentation.composables.character.characterdetail

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription

data class CharacterDetailScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val characterDescription: CharacterDescription? = null
)
