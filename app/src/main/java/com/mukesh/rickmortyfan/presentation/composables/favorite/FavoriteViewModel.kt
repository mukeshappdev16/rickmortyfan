package com.mukesh.rickmortyfan.presentation.composables.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.rickmortyfan.domain.use_cases.favorite.characters.GetFavoriteCharactersUseCase
import com.mukesh.rickmortyfan.domain.use_cases.favorite.episode.GetFavoriteEpisodeUseCase
import com.mukesh.rickmortyfan.domain.use_cases.favorite.locations.GetFavoriteLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
    @Inject
    constructor(
        getFavoriteEpisodeUseCase: GetFavoriteEpisodeUseCase,
        getFavoriteLocationUseCase: GetFavoriteLocationUseCase,
        getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    ) : ViewModel() {
        private val _favoriteState = mutableStateOf(FavoriteState())
        val favoriteState: State<FavoriteState> = _favoriteState

        init {
            // Combine all three flows into one state update
            combine(
                getFavoriteCharactersUseCase(),
                getFavoriteLocationUseCase(),
                getFavoriteEpisodeUseCase(),
            ) { characters, locations, episodes ->
                // Update the state while keeping the data synchronized
                _favoriteState.value =
                    _favoriteState.value.copy(
                        characterList = characters,
                        locationList = locations,
                        episodeList = episodes,
                        isLoading = false, // Assuming you have a loading flag
                    )
            }.launchIn(viewModelScope)
        }
    }
