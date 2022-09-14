package com.velotio.marvelcomic.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.velotio.marvelcomic.presentation.state.CharacterViewState
import com.velotio.marvelcomic.presentation.theme.MarvelComicTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CharacterTile(
    state: CharacterViewState,
    characterSelectAction: () -> Unit,
    bookmarkAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(10),
        onClick = characterSelectAction
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center
        ) {
            Column {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        model = state.imageUrl,
                        loading = {
                            CircularProgressIndicator()
                        },
                        contentScale = ContentScale.Inside,
                        contentDescription = "Character Image"
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 4.dp),
                    text = state.name,
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(
                    modifier = Modifier.height(5.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 8.dp),
                    text = state.description.ifEmpty { "Description not available" },
                    style = MaterialTheme.typography.subtitle2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(bottomStart = 10.dp)
                    )
                    .padding(5.dp)
                    .align(Alignment.TopEnd)
            ) {
                IconButton(
                    modifier = Modifier
                        .size(30.dp),
                    onClick = bookmarkAction
                ) {
                    Icon(
                        imageVector = if (state.bookmarkStatus) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Character Image",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CharacterTilePreview() {

    val bookmarkStatus = remember {
        mutableStateOf(false)
    }

    MarvelComicTheme(false) {
        CharacterTile(
            characterSelectAction = {},
            bookmarkAction = {
                bookmarkStatus.value = bookmarkStatus.value.not()
            },
            state = CharacterViewState(
                id = 0,
                name = "Iron Man aka Tony Stark",
                description = "Description Description Description Description Description Description Description Description Description",
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
                bookmarkStatus = bookmarkStatus.value
            )
        )
    }
}