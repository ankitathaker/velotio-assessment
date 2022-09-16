package com.velotio.marvelcomic.presentation.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.velotio.marvelcomic.R

@Composable
fun Info(
    @StringRes messageResource: Int,
    @DrawableRes iconResource: Int,
    modifier: Modifier = Modifier,
    isInfoOnly: Boolean = true,
    buttonAction: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = iconResource),
            contentDescription = stringResource(id = R.string.info_icon)
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            text = stringResource(id = messageResource),
            style = MaterialTheme.typography.body1
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        if (isInfoOnly.not())
            Button(onClick = buttonAction) {
                Text(
                    text = stringResource(id = R.string.try_again),
                    style = MaterialTheme.typography.caption
                )
            }
    }
}

@Preview
@Composable
fun InfoPreview() {
    Info(
        messageResource = R.string.no_internet,
        iconResource = R.drawable.ic_no_connection,
        isInfoOnly = false
    )
}