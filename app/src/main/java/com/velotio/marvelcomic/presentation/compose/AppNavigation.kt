package com.velotio.marvelcomic.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

/*
* Application Navigation File.
* */

private const val PATH_CHARACTER_ID = "character_id"

sealed class AppScreens(val route: String) {

    object CharactersList : AppScreens("characters")

    object ComicsList : AppScreens("characters/{$PATH_CHARACTER_ID}/comics") {
        fun createRoute(characterId: Long) = "characters/$characterId/comics"
    }
}

@Composable
fun AppNavigation(
    closeAction: () -> Unit,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.CharactersList.route
    ) {

        composable(
            route = AppScreens.CharactersList.route
        ) {
            CharactersListScaffold(
                showComics = { characterId ->
                    navController.navigate(AppScreens.ComicsList.createRoute(characterId))
                },
                closeAction = closeAction,
                modifier = modifier
            )
        }

        composable(
            route = AppScreens.ComicsList.route,
            arguments = listOf(
                navArgument(name = PATH_CHARACTER_ID) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            ComicsListScaffold(
                characterId = backStackEntry.arguments?.getLong(PATH_CHARACTER_ID) ?: 0L,
                navigateUp = navController::navigateUp,
                modifier = modifier
            )
        }
    }
}