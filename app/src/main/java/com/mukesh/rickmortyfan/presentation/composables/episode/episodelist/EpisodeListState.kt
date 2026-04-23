package com.mukesh.rickmortyfan.presentation.composables.episode.episodelist

import com.mukesh.rickmortyfan.domain.modal.episode.Episode

data class EpisodeListState(
    val isLoading: Boolean = false,
    val isPaginating: Boolean = false,
    val errorMessage: String = "",
    val noInternet: Boolean = false,
    val list: List<Episode> = emptyList(),
    val page: Int = 1,
    val endReached: Boolean = false,
)
