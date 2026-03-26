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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModal @Inject constructor(val getEpisodeListUseCase: GetEpisodeListUseCase) :
    ViewModel() {

    private var _episodeListState: MutableState<EpisodeListState> = mutableStateOf(
        EpisodeListState()
    )
    var episodeListState: State<EpisodeListState> = _episodeListState

    init {
        getEpisodes()
        Log.i("EpisodeListViewModal", "EpisodeListViewModal init called")
    }

    fun getEpisodes() {
        viewModelScope.launch {
            getEpisodeListUseCase().collect { result ->
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
            }
        }
    }
}