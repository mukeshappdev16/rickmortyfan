package com.mukesh.rickmortyfan.domain.use_cases.favorite.locations

import com.mukesh.rickmortyfan.domain.repository.FavoriteLocationRepository
import javax.inject.Inject

class GetFavoriteLocationUseCase @Inject constructor(
    val favoriteLocationRepository: FavoriteLocationRepository
) {
    operator fun invoke() = favoriteLocationRepository.getFavoriteLocations()
}