package com.mukesh.rickmortyfan.domain.use_cases

import com.mukesh.rickmortyfan.common.Resource
import com.mukesh.rickmortyfan.domain.modal.character.Characters
import com.mukesh.rickmortyfan.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetCharacterListUseCase @Inject constructor(val characterRepository: CharacterRepository) {
    operator fun invoke(): Flow<Resource<Characters>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(characterRepository.getCharacters()))
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            emit(Resource.Error("Network error. Please try again later"))
        } catch (exc: Exception) {
            exc.printStackTrace()
            emit(Resource.Error("Something went wrong. Please try again later"))
        }
    }
}