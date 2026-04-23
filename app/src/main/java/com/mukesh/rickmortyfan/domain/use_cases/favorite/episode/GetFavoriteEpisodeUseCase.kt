package com.mukesh.rickmortyfan.domain.use_cases.favorite.episode

import com.mukesh.rickmortyfan.domain.repository.FavoriteEpisodeRepository
import javax.inject.Inject

class GetFavoriteEpisodeUseCase
    @Inject
    constructor(
        val favoriteEpisodeRepository: FavoriteEpisodeRepository,
    ) {
        operator fun invoke() = favoriteEpisodeRepository.getFavoriteEpisodes()
    }
