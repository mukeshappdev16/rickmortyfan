package com.mukesh.rickmortyfan.di

import android.content.Context
import androidx.room.Room
import com.mukesh.rickmortyfan.common.Constants
import com.mukesh.rickmortyfan.data.database.FavoritesDao
import com.mukesh.rickmortyfan.data.database.FavoritesDatabase
import com.mukesh.rickmortyfan.data.repository.CharactersRepositoryImpl
import com.mukesh.rickmortyfan.data.repository.EpisodesRepositoryImpl
import com.mukesh.rickmortyfan.data.repository.FavoriteCharacterRepositoryImpl
import com.mukesh.rickmortyfan.data.repository.FavoriteEpisodeRepositoryImpl
import com.mukesh.rickmortyfan.data.repository.FavoriteLocationRepositoryImpl
import com.mukesh.rickmortyfan.data.repository.LocationsRepositoryImpl
import com.mukesh.rickmortyfan.data.retrofit.RickMortyCharacterApi
import com.mukesh.rickmortyfan.data.retrofit.RickMortyEpisodesApi
import com.mukesh.rickmortyfan.data.retrofit.RickMortyLocationApi
import com.mukesh.rickmortyfan.domain.repository.CharactersRepository
import com.mukesh.rickmortyfan.domain.repository.EpisodesRepository
import com.mukesh.rickmortyfan.domain.repository.FavoriteCharacterRepository
import com.mukesh.rickmortyfan.domain.repository.FavoriteEpisodeRepository
import com.mukesh.rickmortyfan.domain.repository.FavoriteLocationRepository
import com.mukesh.rickmortyfan.domain.repository.LocationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideRickMortyLocationApiClient(retrofit: Retrofit): RickMortyLocationApi {
        return retrofit.create(RickMortyLocationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationsRepository(rickMortyLocationApi: RickMortyLocationApi): LocationsRepository {
        return LocationsRepositoryImpl(rickMortyLocationApi)
    }

    @Provides
    @Singleton
    fun provideFavoritesDatabase(@ApplicationContext context: Context): FavoritesDatabase {
        return Room.databaseBuilder(
            context = context,
            FavoritesDatabase::class.java,
            "favorites_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(favoritesDatabase: FavoritesDatabase) = favoritesDatabase.favoritesDao()

    @Provides
    @Singleton
    fun provideFavoritesRepository(favoritesDao: FavoritesDao): FavoriteCharacterRepository {
        return FavoriteCharacterRepositoryImpl(favoritesDao)
    }

    @Provides
    @Singleton
    fun provideFavoriteLocationRepository(favoritesDao: FavoritesDao): FavoriteLocationRepository {
        return FavoriteLocationRepositoryImpl(favoritesDao)
    }

    @Provides
    @Singleton
    fun provideFavoriteEpisodeRepository(favoritesDao: FavoritesDao): FavoriteEpisodeRepository {
        return FavoriteEpisodeRepositoryImpl(favoritesDao)
    }
}
