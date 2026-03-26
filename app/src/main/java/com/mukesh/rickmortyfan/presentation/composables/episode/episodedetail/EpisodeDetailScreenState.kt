package com.mukesh.rickmortyfan.presentation.composables.episode.episodedetail

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.episode.Episode

data class EpisodeDetailScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val episode: Episode? = null,
    val episodeCharList: List<CharacterDescription> = emptyList()
)
