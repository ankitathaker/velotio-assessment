package com.velotio.marvelcomic.presentation.compose

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.velotio.marvelcomic.presentation.UiState
import com.velotio.marvelcomic.presentation.state.CharacterViewState
import com.velotio.marvelcomic.presentation.theme.MarvelComicTheme
import com.velotio.marvelcomic.presentation.view_model.CharactersListViewModel
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CharactersListScaffold(
    closeAction: () -> Unit,
    modifier: Modifier = Modifier,
    charactersListViewModel: CharactersListViewModel = getViewModel()
) {
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Characters")
                },
                navigationIcon = {
                    IconButton(onClick = closeAction) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close Icon"
                        )
                    }
                }
            )
        }
    ) { _ ->
        val state = charactersListViewModel.characters.collectAsState()

        when (state.value) {

            is UiState.Loading -> {
                CircularProgressIndicator()
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
                                        Toast.makeText(
                                            context,
                                            "${state.name} selected!",
                                            Toast.LENGTH_LONG
                                        ).show()
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
                    }
                }
            }

            is UiState.ApiError -> {
                Text(text = (state.value as UiState.ApiError<List<CharacterViewState>>).error.stackTraceToString())
            }

            is UiState.NoInternetError -> {
                Text(text = "No Internet")
            }
        }
    }
}

@Preview
@Composable
private fun CharactersListScaffoldPreview() {
    MarvelComicTheme {
        CharactersListScaffold(closeAction = {})
    }
}