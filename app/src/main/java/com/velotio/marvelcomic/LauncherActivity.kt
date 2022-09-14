package com.velotio.marvelcomic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.velotio.marvelcomic.presentation.compose.CharactersListScaffold
import com.velotio.marvelcomic.presentation.theme.MarvelComicTheme

/*
* Container activity.
* */
class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Content()
        }
    }

    @Composable
    fun Content(modifier: Modifier = Modifier) {
        MarvelComicTheme {
            CharactersListScaffold(
                closeAction = {
                    finish()
                },
                modifier = modifier
            )
        }
    }
}

