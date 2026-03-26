package com.mukesh.rickmortyfan.di

import com.mukesh.rickmortyfan.common.Constants
import com.mukesh.rickmortyfan.data.repository.CharactersRepositoryImpl
import com.mukesh.rickmortyfan.data.repository.EpisodesRepositoryImpl
import com.mukesh.rickmortyfan.data.retrofit.RickMortyCharacterApi
import com.mukesh.rickmortyfan.data.retrofit.RickMortyEpisodesApi
import com.mukesh.rickmortyfan.domain.repository.CharactersRepository
import com.mukesh.rickmortyfan.domain.repository.EpisodesRepository
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
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideRickMortyCharacterApiClient(retrofit: Retrofit): RickMortyCharacterApi {
        return retrofit.create(RickMortyCharacterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(rickMortyApi: RickMortyCharacterApi): CharactersRepository {
        return CharactersRepositoryImpl(rickMortyApi)
    }

    @Provides
    @Singleton
    fun provideRickMortyEpisodesApiClient(retrofit: Retrofit): RickMortyEpisodesApi {
        return retrofit.create(RickMortyEpisodesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEpisodesRepository(rickMortyEpisodesApi: RickMortyEpisodesApi): EpisodesRepository {
        return EpisodesRepositoryImpl(rickMortyEpisodesApi)
    }

}