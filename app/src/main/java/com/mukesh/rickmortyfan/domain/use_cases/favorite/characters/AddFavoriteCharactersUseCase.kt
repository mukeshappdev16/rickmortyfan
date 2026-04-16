package com.mukesh.rickmortyfan.domain.use_cases.favorite.characters

import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.repository.FavoriteCharacterRepository
import javax.inject.Inject

class AddFavoriteCharactersUseCase @Inject constructor(
    val favoriteCharacterRepository: FavoriteCharacterRepository
) {
    suspend operator fun invoke(characterDescription: CharacterDescription) =
        favoriteCharacterRepository.addFavoriteCharacter(characterDescription)
}