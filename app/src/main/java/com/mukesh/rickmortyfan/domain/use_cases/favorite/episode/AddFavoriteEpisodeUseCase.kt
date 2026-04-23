package com.mukesh.rickmortyfan.domain.use_cases.favorite.episode

import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.domain.repository.FavoriteEpisodeRepository
import javax.inject.Inject

class AddFavoriteEpisodeUseCase
    @Inject
    constructor(
        val favoriteEpisodeRepository: FavoriteEpisodeRepository,
    ) {
        suspend operator fun invoke(episode: Episode) = favoriteEpisodeRepository.addFavoriteEpisode(episode)
    }
