package com.mukesh.rickmortyfan.presentation.composables.character.characterdetail

import com.mukesh.rickmortyfan.domain.use_cases.characters.GetCharacterDetailUseCase
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.rickmortyfan.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModal @Inject constructor(val getCharacterDetailUseCase: GetCharacterDetailUseCase) :
    ViewModel() {

    private var _characterDetailScreenState: MutableState<CharacterDetailScreenState> =
        mutableStateOf(
            CharacterDetailScreenState()
        )
    var characterDetailScreenState: State<CharacterDetailScreenState> = _characterDetailScreenState

    fun getCharacterDetail(charId: String) {
        getCharacterDetailUseCase(charId).onEach { result ->

            when (result) {
                is Resource.Loading -> {
                    _characterDetailScreenState.value = CharacterDetailScreenState(isLoading = true)
                }

                is Resource.Success -> {
                    _characterDetailScreenState.value =
                        CharacterDetailScreenState(characterDescription = result.data)
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
}