package com.velotio.marvelcomic.presentation.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.velotio.marvelcomic.R
import com.velotio.marvelcomic.presentation.UiState
import com.velotio.marvelcomic.presentation.theme.MarvelComicTheme
import com.velotio.marvelcomic.presentation.view_model.CharactersListViewModel
import org.koin.androidx.compose.getViewModel

/*
* Scaffold(Layout) for Characters list page
* */


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CharactersListScaffold(
    showComics: (Long) -> Unit,
    closeAction: () -> Unit,
    modifier: Modifier = Modifier,
    charactersListViewModel: CharactersListViewModel = getViewModel()
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.characters))
                },
                navigationIcon = {
                    IconButton(onClick = closeAction) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.close_icon)
                        )
                    }
                }
            )
        }
    ) {
        val state = charactersListViewModel.characters.collectAsState()

        when (state.value) {

            is UiState.Loading -> {
                Loader()
            }

            is UiState.Loaded -> {
                state.value.data?.let { characters ->
                    val isRefreshing = remember { mutableStateOf(false) }
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
                        onRefresh = {
                            isRefreshing.value = true
                            charactersListViewModel.refresh()
                        }
                    ) {
                        isRefreshing.value = false

                        if (characters.isNotEmpty()) {

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxSize()
                            ) {
                                items(characters) { state ->
                                    CharacterTile(
                                        state = state,
                                        characterSelectAction = {
                                            showComics(state.id)
                                        },
                                        bookmarkAction = {
                                            charactersListViewModel.bookmarkCharacter(state.id)
                                        },
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .fillMaxHeight(fraction = 0.35f)
                                    )
                                }
                            }

                        } else {
                            Info(
                                messageResource = R.string.no_characters_available,
                                iconResource = R.drawable.ic_no_data
                            )
                        }
                    }
                }
            }

            is UiState.ApiError -> {
                Info(
                    messageResource = R.string.api_error,
                    iconResource = R.drawable.ic_something_went_wrong
                )
            }

            is UiState.NoInternetError -> {
                Info(
                    messageResource = R.string.no_internet,
                    iconResource = R.drawable.ic_no_connection,
                    isInfoOnly = false,
                    buttonAction = {
                        charactersListViewModel.refresh(showLoader = true)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun CharactersListScaffoldPreview() {
    MarvelComicTheme {
        CharactersListScaffold(showComics = {}, closeAction = {})
    }
}