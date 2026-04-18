package com.mukesh.rickmortyfan.presentation.composables.episode.episodelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.common.NetworkManager
import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.use_cases.episodes.GetEpisodeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val getEpisodeListUseCase: GetEpisodeListUseCase
) : ViewModel() {

    private val _episodeListState: MutableState<EpisodeListState> =
        mutableStateOf(EpisodeListState())
    val episodeListState: State<EpisodeListState> = _episodeListState

    init {
        getEpisodes()
    }

    fun getEpisodes() {
        if (_episodeListState.value.endReached || _episodeListState.value.isPaginating) return

        if (!networkManager.isNetworkAvailable()) {
            if (_episodeListState.value.list.isEmpty()) {
                _episodeListState.value = _episodeListState.value.copy(noInternet = true)
            }
            return
        }

        val isFirstPage = _episodeListState.value.page == 1

        getEpisodeListUseCase(_episodeListState.value.page).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    if (isFirstPage) {
                        _episodeListState.value = _episodeListState.value.copy(isLoading = true)
                    } else {
                        _episodeListState.value = _episodeListState.value.copy(isPaginating = true)
                    }
                }

                is Resource.Success -> {
                    val newList = result.data?.episodes ?: emptyList()
                    _episodeListState.value = _episodeListState.value.copy(
                        isLoading = false,
                        isPaginating = false,
                        list = _episodeListState.value.list + newList,
                        page = _episodeListState.value.page + 1,
                        endReached = newList.isEmpty() || result.data?.info?.next == null,
                        noInternet = false,
                        errorMessage = ""
                    )
                }

                is Resource.Error -> {
                    _episodeListState.value = _episodeListState.value.copy(
                        isLoading = false,
                        isPaginating = false,
                        errorMessage = if (isFirstPage) result.message ?: "An error occurred" else ""
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
