package com.mukesh.rickmortyfan.domain.use_cases.favorite.locations

import com.mukesh.rickmortyfan.domain.repository.FavoriteLocationRepository
import javax.inject.Inject

class IsFavoriteLocationUseCase
    @Inject
    constructor(
        val favoriteLocationRepository: FavoriteLocationRepository,
    ) {
        suspend operator fun invoke(locationId: Int): Boolean {
            return favoriteLocationRepository.isFavoriteLocationPresent(locationId)
        }
    }
