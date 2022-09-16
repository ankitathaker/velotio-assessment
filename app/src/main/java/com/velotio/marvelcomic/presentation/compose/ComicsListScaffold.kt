package com.velotio.marvelcomic.presentation.compose

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.velotio.marvelcomic.R
import com.velotio.marvelcomic.presentation.UiState
import com.velotio.marvelcomic.presentation.state.ComicViewState
import com.velotio.marvelcomic.presentation.theme.MarvelComicTheme
import com.velotio.marvelcomic.presentation.view_model.ComicListViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

/*
* Scaffold(Layout) for Comics list page
* */

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ComicsListScaffold(
    characterId: Long,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    comicListViewModel: ComicListViewModel = getViewModel(
        parameters = {
            parametersOf(
                characterId
            )
        }
    )
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.comics))
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_icon)
                        )
                    }
                }
            )
        }
    ) {
        val state = comicListViewModel.comics.collectAsState()

        when (state.value) {

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Loaded -> {
                state.value.data?.let { comics ->
                    ComicsListLayout(
                        state = comics
                    )
                }
            }

            is UiState.ApiError -> {
                Text(text = (state.value as UiState.ApiError<List<ComicViewState>>).error.stackTraceToString())
            }

            is UiState.NoInternetError -> {
                Text(text = "No Internet")
            }
        }
    }
}

@Preview
@Composable
private fun ComicsListScaffoldPreview() {
    MarvelComicTheme {
        ComicsListScaffold(characterId = 1012717, navigateUp = {})
    }
}