package com.mukesh.rickmortyfan.domain.use_cases.characters

import android.util.Log
import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.modal.character.Characters
import com.mukesh.rickmortyfan.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetCharacterListUseCase @Inject constructor(private val characterRepository: CharactersRepository) {
    operator fun invoke(page: Int): Flow<Resource<Characters>> = flow {
        Log.d("GetCharacterListUseCase", "invoke: $page")
        try {
            emit(Resource.Loading())
            emit(Resource.Success(characterRepository.getAllCharacters(page)))
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            emit(Resource.Error("Something went wrong. Please try again later"))
        } catch (exc: Exception) {
            exc.printStackTrace()
            emit(Resource.Error("Something went wrong. Please try again later"))
        }
    }
}