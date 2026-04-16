package com.mukesh.rickmortyfan.domain.use_cases.favorite.characters

import com.mukesh.rickmortyfan.domain.repository.FavoriteCharacterRepository
import javax.inject.Inject

class GetFavoriteCharactersUseCase @Inject constructor(
    val favoriteCharacterRepository: FavoriteCharacterRepository
) {
    operator fun invoke() = favoriteCharacterRepository.getFavoriteCharacters()
}