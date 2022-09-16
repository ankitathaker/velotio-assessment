package com.velotio.marvelcomic.presentation.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.velotio.marvelcomic.R
import com.velotio.marvelcomic.presentation.state.ComicViewState
import com.velotio.marvelcomic.presentation.theme.MarvelComicTheme

/*
* Single entity card for Comic
* */

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ComicTile(
    state: ComicViewState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(0)
    ) {

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 0.dp)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp),
                model = state.imageUrl,
                loading = {
                    CircularProgressIndicator()
                },
                contentScale = ContentScale.Inside,
                contentDescription = stringResource(id = R.string.comic_image)
            )
            Spacer(
                modifier = Modifier
                    .width(5.dp)
            )
            Column {
                Text(
                    text = state.title,
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Spacer(
                    modifier = Modifier.height(5.dp)
                )
                Text(
                    text = state.description.ifEmpty { stringResource(id = R.string.description_not_available) },
                    style = MaterialTheme.typography.subtitle2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }
}

@Preview
@Composable
private fun ComicTilePreview() {
    MarvelComicTheme(false) {
        ComicTile(
            state = ComicViewState(
                id = 0,
                title = "Captain Marvel (2019) #39",
                description = "In search of answers about her mother and her own past, Wanda (and Agatha Harkness) once more set foot on the Witches’ Road for a perilous  journey to the very end of it and the heart and soul of witchcraft itself. Along the way Wanda must face friends and foes from her bizarre life – all of whom conspire to stop her in her quest!",
                imageUrl = "http://gateway.marvel.com/v1/public/comics/95785"
            )
        )
    }
}