package com.mukesh.rickmortyfan.presentation.composables.character.characterList

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.common.NetworkManager
import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.domain.use_cases.characters.GetCharacterListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val getCharacterListUseCase: GetCharacterListUseCase
) : ViewModel() {

    private val _characterListState: MutableState<CharacterListState> =
        mutableStateOf(CharacterListState())
    val characterListState: State<CharacterListState> = _characterListState

    init {
        getCharacters()
        Log.i("CharacterListViewModel", "CharacterListViewModel init called")
    }

    fun getCharacters() {
        if (!networkManager.isNetworkAvailable()) {
            _characterListState.value = CharacterListState(noInternet = true)
            return
        }
        getCharacterListUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _characterListState.value = CharacterListState(isLoading = true)
                }

                is Resource.Success -> {
                    _characterListState.value =
                        CharacterListState(list = result.data?.charactersList ?: emptyList())
                }

                is Resource.Error -> {
                    _characterListState.value =
                        CharacterListState(
                            errorMessage = result.message
                                ?: "Something went wrong. Please try again later"
                        )
                }
            }
        }.launchIn(viewModelScope)
    }
}