package com.mukesh.rickmortyfan.di

import com.mukesh.rickmortyfan.common.Constants
import com.mukesh.rickmortyfan.data.repository.CharactersRepositoryImpl
import com.mukesh.rickmortyfan.data.retrofit.RickMortyCharacterApi
import com.mukesh.rickmortyfan.domain.repository.CharactersRepository
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
    fun provideRetrofitClient(): RickMortyCharacterApi {
        return Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RickMortyCharacterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(rickMortyApi: RickMortyCharacterApi): CharactersRepository {
        return CharactersRepositoryImpl(rickMortyApi)
    }
}