package com.mukesh.rickmortyfan.domain.use_cases.episodes

import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.modal.episode.Episodes
import com.mukesh.rickmortyfan.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetEpisodeListUseCase @Inject constructor(private val episodesRepository: EpisodesRepository) {
    operator fun invoke(page: Int): Flow<Resource<Episodes>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(episodesRepository.getAllEpisodes(page)))
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            emit(Resource.Error("Something went wrong. Please try again later"))
        } catch (exc: Exception) {
            exc.printStackTrace()
            emit(Resource.Error("Something went wrong. Please try again later"))
        }
    }
}