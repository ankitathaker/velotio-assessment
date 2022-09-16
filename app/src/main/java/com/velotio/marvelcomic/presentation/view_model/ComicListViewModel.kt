package com.velotio.marvelcomic.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velotio.marvelcomic.domain.interactors.GetCharacter
import com.velotio.marvelcomic.domain.interactors.GetComics
import com.velotio.marvelcomic.domain.mapper.toViewState
import com.velotio.marvelcomic.presentation.UiState
import com.velotio.marvelcomic.presentation.state.ComicsViewState
import kotlinx.coroutines.flow.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/*
* View Model for Comics page
* */
class ComicListViewModel(
    characterId: Long,
    private val getComics: GetComics,
    private val getCharacter: GetCharacter
) : ViewModel() {

    private val _comics = MutableStateFlow<UiState<ComicsViewState>>(UiState.Loading())
    val comics: StateFlow<UiState<ComicsViewState>> = _comics

    init {
        getAllComics(characterId = characterId)
    }

    private fun getAllComics(characterId: Long, forceRefresh: Boolean = false) {
        getComics(forceRefresh, characterId)
            .catch { error ->
                error.printStackTrace()
                when (error) {
                    is UnknownHostException, is ConnectException, is SocketTimeoutException -> _comics.value =
                        UiState.NoInternetError(error)
                    else -> _comics.value = UiState.ApiError(error)
                }
            }
            .combine(
                getCharacter(characterId)
            ) { comicsList, character ->
                _comics.value = UiState.Loaded(
                    ComicsViewState(
                        characterViewState = character.toViewState(),
                        comicsList = comicsList.toViewState()
                    )
                )
            }
            .launchIn(viewModelScope)
    }

}