package com.mukesh.rickmortyfan.presentation.composables.character.characterList

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription

data class CharacterListState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val list: List<CharacterDescription> = emptyList()
)
