package com.mukesh.rickmortyfan.presentation.composables.episode.episodelist

import com.mukesh.rickmortyfan.domain.modal.episode.Episode

data class EpisodeListState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val list: List<Episode> = emptyList()
)
