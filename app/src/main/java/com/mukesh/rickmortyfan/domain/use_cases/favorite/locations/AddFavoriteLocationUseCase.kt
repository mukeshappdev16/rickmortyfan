package com.mukesh.rickmortyfan.domain.use_cases.favorite.locations

import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import com.mukesh.rickmortyfan.domain.repository.FavoriteLocationRepository
import javax.inject.Inject

class AddFavoriteLocationUseCase
    @Inject
    constructor(
        val favoriteLocationRepository: FavoriteLocationRepository,
    ) {
        suspend operator fun invoke(locationDetail: LocationDetail): Long {
            return favoriteLocationRepository.addFavoriteLocation(locationDetail)
        }
    }
