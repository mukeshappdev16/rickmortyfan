package com.mukesh.rickmortyfan.presentation.composables.character.characterdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.common.NetworkManager
import com.mukesh.common.Resource
import com.mukesh.common.Utils
import com.mukesh.rickmortyfan.domain.use_cases.characters.GetCharacterDetailUseCase
import com.mukesh.rickmortyfan.domain.use_cases.episodes.GetMultipleEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val getMultipleEpisodesUseCase: GetMultipleEpisodesUseCase
) : ViewModel() {

    private val _characterDetailScreenState: MutableState<CharacterDetailScreenState> =
        mutableStateOf(CharacterDetailScreenState())
    val characterDetailScreenState: State<CharacterDetailScreenState> = _characterDetailScreenState

    fun getCharacterDetail(charId: String) {
        if (!networkManager.isNetworkAvailable()) {
            _characterDetailScreenState.value = CharacterDetailScreenState(noInternet = true)
            return
        }
        getCharacterDetailUseCase(charId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _characterDetailScreenState.value = CharacterDetailScreenState(isLoading = true)
                }

                is Resource.Success -> {
                    _characterDetailScreenState.value =
                        CharacterDetailScreenState(characterDescription = result.data)
                    result.data?.episode?.let { idsList ->
                        getAllEpisodesForCharacter(Utils.getIdsFromUrlList(idsList))
                    }
                }

                is Resource.Error -> {
                    _characterDetailScreenState.value =
                        CharacterDetailScreenState(
                            errorMessage = result.message
                                ?: "Something went wrong. Please try again later"
                        )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAllEpisodesForCharacter(episodeUrlList: List<String>) {
        getMultipleEpisodesUseCase(episodeUrlList).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    // Do Nothing or display error message for episode section
                }

                is Resource.Error -> {
                    // Do Nothing or display error message for episode section
                }

                is Resource.Success -> {
                    result.data?.let {
                        _characterDetailScreenState.value =
                            _characterDetailScreenState.value.copy(
                                episodes = it
                            )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}