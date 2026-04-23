package com.mukesh.rickmortyfan.domain.use_cases.favorite.episode

import com.mukesh.rickmortyfan.domain.repository.FavoriteEpisodeRepository
import javax.inject.Inject

class IsFavoriteEpisodeUseCase
    @Inject
    constructor(
        val favoriteEpisodeRepository: FavoriteEpisodeRepository,
    ) {
        suspend operator fun invoke(charId: String) = favoriteEpisodeRepository.isFavoriteEpisodePresent(charId.toInt())
    }
