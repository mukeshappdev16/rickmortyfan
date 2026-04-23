package com.mukesh.rickmortyfan.domain.use_cases.favorite

import com.mukesh.rickmortyfan.domain.repository.AllFavoritesRepository
import javax.inject.Inject

class ClearAllFavoritesUseCase
    @Inject
    constructor(
        private val allFavoritesRepository: AllFavoritesRepository,
    ) {
        suspend operator fun invoke() {
            allFavoritesRepository.clearAllFavorites()
        }
    }
