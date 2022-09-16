package com.velotio.marvelcomic.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velotio.marvelcomic.domain.interactors.GetCharacters
import com.velotio.marvelcomic.domain.interactors.ToggleCharacterBookmarkStatus
import com.velotio.marvelcomic.domain.mapper.toViewState
import com.velotio.marvelcomic.presentation.UiState
import com.velotio.marvelcomic.presentation.state.CharacterViewState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/*
* View Model for character page
* */

class CharactersListViewModel(
    private val getCharacters: GetCharacters,
    private val toggleCharacterBookmarkStatus: ToggleCharacterBookmarkStatus
) : ViewModel() {

    private val _characters = MutableStateFlow<UiState<List<CharacterViewState>>>(UiState.Loading())
    val characters: StateFlow<UiState<List<CharacterViewState>>> = _characters

    init {
        _characters.value = UiState.Loading()
        getAllCharacters()
    }

    private fun getAllCharacters(forceRefresh: Boolean = false) {
        getCharacters(forceRefresh)
            .catch { error ->
                error.printStackTrace()
                when (error) {
                    is UnknownHostException, is ConnectException, is SocketTimeoutException -> _characters.value =
                        UiState.NoInternetError(error)
                    else -> _characters.value = UiState.ApiError(error)
                }
            }.map { list ->
                _characters.value = UiState.Loaded(list.toViewState())
            }.launchIn(viewModelScope)
    }

    fun refresh(showLoader: Boolean = false) {
        if (showLoader) {
            _characters.value = UiState.Loading()
        }
        getAllCharacters(forceRefresh = true)
    }

    fun bookmarkCharacter(characterId: Long) {
        viewModelScope.launch {
            toggleCharacterBookmarkStatus(characterId = characterId)
        }
    }
}