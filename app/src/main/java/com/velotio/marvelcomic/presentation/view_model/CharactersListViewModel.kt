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

class CharactersListViewModel(
    private val getCharacters: GetCharacters,
    private val toggleCharacterBookmarkStatus: ToggleCharacterBookmarkStatus
) : ViewModel() {

    private val _characters = MutableStateFlow<UiState<List<CharacterViewState>>>(UiState.Loading())
    val characters: StateFlow<UiState<List<CharacterViewState>>> = _characters

    init {
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

        /*viewModelScope.launch {

            try {

                val listFlow = getCharacters.invoke()

                listFlow.map { list ->
                    _characters.value = UiState.Loaded(list.toViewState())
                }

                *//*_characters.value = UiState.Loaded(
                    listOf(
                        CharacterViewState(
                            name = "Iron Man aka Tony Stark",
                            description = "Description Description Description Description Description Description Description Description Description",
                            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"
                        ),
                        CharacterViewState(
                            name = "Captain America",
                            description = "Description Description Description Description Description Description Description Description Description",
                            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"
                        ),
                        CharacterViewState(
                            name = "Black Widow",
                            description = "Description Description Description Description Description Description Description Description Description",
                            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"
                        ),
                        CharacterViewState(
                            name = "Mighty Thor",
                            description = "Description Description Description Description Description Description Description Description Description",
                            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"
                        ),
                        CharacterViewState(
                            name = "Spider-Man",
                            description = "Description Description Description Description Description Description Description Description Description",
                            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"
                        )
                    )
                )*//*

            } catch (e: Throwable) {
                e.printStackTrace()
                when (e) {
                    is UnknownHostException, is ConnectException, is SocketTimeoutException -> _characters.value =
                        UiState.NoInternetError(e)
                    else -> _characters.value = UiState.ApiError(e)
                }

            }

    }*/
    }

    fun refresh() = getAllCharacters(forceRefresh = true)

    fun bookmarkCharacter(characterId: Long) {
        viewModelScope.launch {
            toggleCharacterBookmarkStatus(characterId = characterId)
        }
    }
}