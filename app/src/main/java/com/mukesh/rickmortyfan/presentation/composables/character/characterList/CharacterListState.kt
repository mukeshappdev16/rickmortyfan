package com.mukesh.rickmortyfan.presentation.composables.character.characterList

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription

data class CharacterListState(
    val noInternet: Boolean = false,
    val isLoading: Boolean = false,
    val isPaginating: Boolean = false,
    val errorMessage: String = "",
    val list: List<CharacterDescription> = emptyList(),
    val page: Int = 1,
    val endReached: Boolean = false,
)
