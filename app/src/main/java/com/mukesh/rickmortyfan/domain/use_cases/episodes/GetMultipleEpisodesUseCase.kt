package com.mukesh.rickmortyfan.domain.use_cases.episodes

import com.mukesh.rickmortyfan.common.Resource
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetMultipleEpisodesUseCase @Inject constructor(private val characterRepository: EpisodesRepository) {
    operator fun invoke(ids: List<String>): Flow<Resource<List<Episode>>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(characterRepository.getMultipleEpisodes(ids)))
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            emit(Resource.Error("Network error. Please try again later"))
        } catch (exc: Exception) {
            exc.printStackTrace()
            emit(Resource.Error("Something went wrong. Please try again later"))
        }
    }
}