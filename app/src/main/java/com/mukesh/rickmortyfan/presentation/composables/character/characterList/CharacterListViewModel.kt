package com.mukesh.rickmortyfan.presentation.composables.character.characterList

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
    }

    fun getCharacters() {
        if (_characterListState.value.endReached || _characterListState.value.isPaginating) return

        if (!networkManager.isNetworkAvailable()) {
            if (_characterListState.value.list.isEmpty()) {
                _characterListState.value = _characterListState.value.copy(noInternet = true)
            }
            return
        }

        val isFirstPage = _characterListState.value.page == 1

        getCharacterListUseCase(_characterListState.value.page).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    if (isFirstPage) {
                        _characterListState.value = _characterListState.value.copy(isLoading = true)
                    } else {
                        _characterListState.value = _characterListState.value.copy(isPaginating = true)
                    }
                }

                is Resource.Success -> {
                    val newList = result.data?.charactersList ?: emptyList()
                    _characterListState.value = _characterListState.value.copy(
                        isLoading = false,
                        isPaginating = false,
                        list = _characterListState.value.list + newList,
                        page = _characterListState.value.page + 1,
                        endReached = newList.isEmpty() || result.data?.info?.next == null,
                        noInternet = false,
                        errorMessage = ""
                    )
                }

                is Resource.Error -> {
                    _characterListState.value = _characterListState.value.copy(
                        isLoading = false,
                        isPaginating = false,
                        errorMessage = if (isFirstPage) result.message ?: "An error occurred" else ""
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
