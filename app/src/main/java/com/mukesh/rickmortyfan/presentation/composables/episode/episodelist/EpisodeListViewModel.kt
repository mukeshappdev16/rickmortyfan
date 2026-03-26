package com.mukesh.rickmortyfan.presentation.composables.episode.episodelist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.rickmortyfan.common.Resource
import com.mukesh.rickmortyfan.domain.use_cases.episodes.GetEpisodeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    private val getEpisodeListUseCase: GetEpisodeListUseCase
) : ViewModel() {

    private val _episodeListState: MutableState<EpisodeListState> = 
        mutableStateOf(EpisodeListState())
    val episodeListState: State<EpisodeListState> = _episodeListState

    init {
        getEpisodes()
        Log.i("EpisodeListViewModel", "EpisodeListViewModel init called")
    }

    fun getEpisodes() {
        getEpisodeListUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _episodeListState.value = EpisodeListState(isLoading = true)
                }

                is Resource.Success -> {
                    _episodeListState.value =
                        EpisodeListState(list = result.data?.episodes ?: emptyList())
                }

                is Resource.Error -> {
                    _episodeListState.value =
                        EpisodeListState(
                            errorMessage = result.message
                                ?: "Something went wrong. Please try again later"
                        )
                }
            }
        }.launchIn(viewModelScope)
    }
}