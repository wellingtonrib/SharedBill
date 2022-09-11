package br.com.jwar.sharedbill.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.Auth
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.Home
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthScreen
import br.com.jwar.sharedbill.presentation.ui.screens.home.HomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
@ExperimentalAnimationApi
fun NavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Home.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = Auth.route
        ) {
            AuthScreen(navController = navController)
        }
        composable(
            route = Home.route
        ) {
            HomeScreen(navController = navController)
        }
    }
}