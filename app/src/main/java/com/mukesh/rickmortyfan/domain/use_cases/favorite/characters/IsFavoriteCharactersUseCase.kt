package com.mukesh.rickmortyfan.domain.use_cases.favorite.characters

import com.mukesh.rickmortyfan.domain.repository.FavoriteCharacterRepository
import javax.inject.Inject

class IsFavoriteCharactersUseCase
    @Inject
    constructor(
        val favoriteCharacterRepository: FavoriteCharacterRepository,
    ) {
        suspend operator fun invoke(charId: String) = favoriteCharacterRepository.isFavoriteCharacterPresent(charId.toInt())
    }
