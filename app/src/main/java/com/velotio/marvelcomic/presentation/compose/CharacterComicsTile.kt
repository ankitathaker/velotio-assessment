package com.velotio.marvelcomic.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.velotio.marvelcomic.presentation.state.CharacterViewState

@Composable
fun CharacterComicsTile(
    state: CharacterViewState,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .background(color = MaterialTheme.colors.surface)
            .padding(vertical = 8.dp, horizontal = 0.dp)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .width(150.dp),
            model = state.imageUrl,
            loading = {
                CircularProgressIndicator()
            },
            contentScale = ContentScale.Inside,
            contentDescription = stringResource(id = R.string.character_image)
        )
        Spacer(
            modifier = Modifier
                .width(5.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.character_name),
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(
                text = state.name,
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                text = stringResource(id = R.string.description),
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(
                text = state.description.ifEmpty { stringResource(id = R.string.description_not_available) },
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}

@Preview
@Composable
private fun CharacterComicsTilePreview() {
    CharacterComicsTile(
        state = CharacterViewState(
            id = 0,
            name = "Iron Man aka Tony Stark",
            description = "Description Description Description Description Description Description Description Description Description",
            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
            bookmarkStatus = false
        )
    )
}