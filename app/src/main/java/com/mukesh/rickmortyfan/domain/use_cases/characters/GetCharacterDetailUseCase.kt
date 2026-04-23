package com.mukesh.rickmortyfan.domain.use_cases.characters

import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetCharacterDetailUseCase
    @Inject
    constructor(private val characterRepository: CharactersRepository) {
        operator fun invoke(charId: String): Flow<Resource<CharacterDescription>> =
            flow {
                try {
                    emit(Resource.Loading())
                    emit(Resource.Success(characterRepository.getCharacterDetail(charId)))
                } catch (httpException: HttpException) {
                    httpException.printStackTrace()
                    emit(Resource.Error("Something went wrong. Please try again later"))
                } catch (exc: Exception) {
                    exc.printStackTrace()
                    emit(Resource.Error("Something went wrong. Please try again later"))
                }
            }
    }
