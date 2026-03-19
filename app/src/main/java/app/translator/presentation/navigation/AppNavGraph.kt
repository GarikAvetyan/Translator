package app.translator.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.hilt.navigation.compose.hiltViewModel
import app.translator.presentation.learn.detail.screen.LearnModuleScreen
import app.translator.presentation.learn.list.screen.LearnSpanishScreen
import app.translator.presentation.phrasebook.detail.screen.PhrasebookCategoryScreen
import app.translator.presentation.phrasebook.list.screen.PhrasebookScreen
import app.translator.presentation.settings.screen.SettingsScreen
import app.translator.presentation.translator.screen.TranslatorScreen
import app.translator.presentation.translator.component.BottomNavItem

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Translator,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(Routes.Translator) {
            TranslatorScreen(
                viewModel = hiltViewModel(),
                selectedRoute = Routes.Translator,
                onBottomNavSelected = { item -> navigateBottom(navController, item) },
                onSettingsClick = { navController.navigate(Routes.Settings) }
            )
        }
        composable(Routes.LearnSpanish) {
            LearnSpanishScreen(
                viewModel = hiltViewModel(),
                onModuleSelected = { type ->
                    navController.navigate("${Routes.LearnModule}/${type.name}")
                },
                onBack = { navController.popBackStack() },
                onSettings = { navController.navigate(Routes.Settings) },
                selectedRoute = Routes.LearnSpanish,
                onBottomNavSelected = { item -> navigateBottom(navController, item) }
            )
        }
        composable(Routes.Phrasebook) {
            PhrasebookScreen(
                viewModel = hiltViewModel(),
                onCategorySelected = { type ->
                    navController.navigate("${Routes.PhrasebookCategory}/${type.name}")
                },
                onBack = { navController.popBackStack() },
                onSettings = { navController.navigate(Routes.Settings) },
                selectedRoute = Routes.Phrasebook,
                onBottomNavSelected = { item -> navigateBottom(navController, item) }
            )
        }
        composable(Routes.Settings) {
            SettingsScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "${Routes.LearnModule}/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) {
            LearnModuleScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() },
                onSettings = { navController.navigate(Routes.Settings) }
            )
        }
        composable(
            route = "${Routes.PhrasebookCategory}/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) {
            PhrasebookCategoryScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() },
                onSettings = { navController.navigate(Routes.Settings) }
            )
        }
    }
}

private fun navigateBottom(
    navController: NavHostController,
    item: BottomNavItem
) {
    navController.navigate(item.route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(Routes.Translator) { saveState = true }
    }
}
