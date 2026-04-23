package com.mukesh.rickmortyfan.presentation.composables.episode.episodedetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.common.NetworkManager
import com.mukesh.common.Resource
import com.mukesh.common.Utils
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.domain.use_cases.characters.GetMultipleCharacterUseCase
import com.mukesh.rickmortyfan.domain.use_cases.episodes.GetEpisodeDetailUseCase
import com.mukesh.rickmortyfan.domain.use_cases.favorite.episode.AddFavoriteEpisodeUseCase
import com.mukesh.rickmortyfan.domain.use_cases.favorite.episode.IsFavoriteEpisodeUseCase
import com.mukesh.rickmortyfan.domain.use_cases.favorite.episode.RemoveFavoriteEpisodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel
    @Inject
    constructor(
        private val networkManager: NetworkManager,
        private val getEpisodeDetailUseCase: GetEpisodeDetailUseCase,
        private val getMultipleCharacterUseCase: GetMultipleCharacterUseCase,
        private val isFavoriteEpisodeUseCase: IsFavoriteEpisodeUseCase,
        private val addFavoriteEpisodeUseCase: AddFavoriteEpisodeUseCase,
        private val removeFavoriteEpisodeUseCase: RemoveFavoriteEpisodeUseCase,
    ) : ViewModel() {
        private val _episodeDetailScreenState: MutableState<EpisodeDetailScreenState> =
            mutableStateOf(EpisodeDetailScreenState())
        val episodeDetailScreenState: State<EpisodeDetailScreenState> = _episodeDetailScreenState

        fun getEpisodeDetail(episodeId: String) {
            if (!networkManager.isNetworkAvailable()) {
                _episodeDetailScreenState.value = EpisodeDetailScreenState(noInternet = true)
                return
            }
            getEpisodeDetailUseCase(episodeId).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _episodeDetailScreenState.value = EpisodeDetailScreenState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _episodeDetailScreenState.value =
                            EpisodeDetailScreenState(episode = result.data)
                        result.data?.characters?.let { idsList ->
                            getAllCharactersForEpisode(Utils.getIdsFromUrlList(idsList))
                        }
                        isFavoriteEpisode(episodeId)
                    }

                    is Resource.Error -> {
                        _episodeDetailScreenState.value =
                            EpisodeDetailScreenState(
                                errorMessage =
                                    result.message
                                        ?: "Something went wrong. Please try again later",
                            )
                    }
                }
            }.launchIn(viewModelScope)
        }

        private fun isFavoriteEpisode(episodeId: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val isFavorite = isFavoriteEpisodeUseCase(episodeId)
                _episodeDetailScreenState.value =
                    _episodeDetailScreenState.value.copy(isFavorite = isFavorite)
            }
        }

        fun addFavoriteEpisode(episode: Episode) {
            viewModelScope.launch {
                val isAdded = addFavoriteEpisodeUseCase(episode)
                if (isAdded > 0) {
                    _episodeDetailScreenState.value =
                        _episodeDetailScreenState.value.copy(isFavorite = true)
                }
            }
        }

        fun removeFavoriteEpisode(episode: Episode) {
            viewModelScope.launch {
                val isRemoved = removeFavoriteEpisodeUseCase(episode)
                if (isRemoved == 1) {
                    _episodeDetailScreenState.value =
                        _episodeDetailScreenState.value.copy(isFavorite = false)
                }
            }
        }

        private fun getAllCharactersForEpisode(charIdsList: List<String>) {
            getMultipleCharacterUseCase(charIdsList).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        // Do Nothing or display error message for episode section
                    }

                    is Resource.Error -> {
                        // Do Nothing or display error message for episode section
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            _episodeDetailScreenState.value =
                                _episodeDetailScreenState.value.copy(
                                    episodeCharList = it,
                                )
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
