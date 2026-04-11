package com.mukesh.rickmortyfan.domain.use_cases.locations

import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.modal.location.Locations
import com.mukesh.rickmortyfan.domain.repository.LocationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetLocationListUseCase @Inject constructor(
    private val repository: LocationsRepository
) {
    operator fun invoke(): Flow<Resource<Locations>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repository.getAllLocations()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Something went wrong. Please try again later"))
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong. Please try again later"))
        }
    }
}
