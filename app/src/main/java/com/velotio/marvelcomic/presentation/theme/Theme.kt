package com.velotio.marvelcomic.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColors = lightColors(
    primary = primary,
    primaryVariant = primaryVariant,
    onPrimary = onPrimary,
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    onSecondary = onSecondary,
    error = error,
    onError = onError,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface
)

private val DarkColors = darkColors(
    primary = onPrimary,
    primaryVariant = onPrimary,
    onPrimary = primary,
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    onSecondary = onSecondary,
    error = error,
    onError = onError,
    background = onBackground,
    onBackground = background,
    surface = onSurface,
    onSurface = surface
)

@Composable
fun MarvelComicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = MarvelComicTypography,
        shapes = MarvelComicShapes,
        content = content
    )
}