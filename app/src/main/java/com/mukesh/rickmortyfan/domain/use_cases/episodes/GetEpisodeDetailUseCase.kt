package com.mukesh.rickmortyfan.domain.use_cases.episodes

import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetEpisodeDetailUseCase
    @Inject
    constructor(private val episodesRepository: EpisodesRepository) {
        operator fun invoke(episodeId: String): Flow<Resource<Episode>> =
            flow {
                try {
                    emit(Resource.Loading())
                    emit(Resource.Success(episodesRepository.getEpisodeDetail(episodeId)))
                } catch (httpException: HttpException) {
                    httpException.printStackTrace()
                    emit(Resource.Error("Something went wrong. Please try again later"))
                } catch (exc: Exception) {
                    exc.printStackTrace()
                    emit(Resource.Error("Something went wrong. Please try again later"))
                }
            }
    }
