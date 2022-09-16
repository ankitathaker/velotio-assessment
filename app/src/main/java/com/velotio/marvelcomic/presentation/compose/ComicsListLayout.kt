package com.velotio.marvelcomic.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.velotio.marvelcomic.presentation.state.ComicsViewState

@Composable
fun ComicsListLayout(
    state: ComicsViewState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        CharacterComicsTile(
            state = state.characterViewState,
            modifier = Modifier
                .fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .padding(5.dp)
        ) {
            items(state.comicsList) { comicState ->
                ComicTile(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    state = comicState
                )
            }
        }
    }
}