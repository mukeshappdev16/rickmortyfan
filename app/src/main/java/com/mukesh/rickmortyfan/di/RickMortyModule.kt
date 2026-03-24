package com.mukesh.rickmortyfan.di

import com.mukesh.rickmortyfan.common.Constants
import com.mukesh.rickmortyfan.data.repository.CharacterRepositoryImpl
import com.mukesh.rickmortyfan.data.retrofit.RickMortyApi
import com.mukesh.rickmortyfan.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RickMortyModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(): RickMortyApi {
        return Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RickMortyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(rickMortyApi: RickMortyApi): CharacterRepository {
        return CharacterRepositoryImpl(rickMortyApi)
    }
}