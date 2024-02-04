package br.com.jwar.sharedbill.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.jwar.sharedbill.account.presentation.navigation.AUTH_ROUTE
import br.com.jwar.sharedbill.account.presentation.navigation.accountNav
import br.com.jwar.sharedbill.groups.presentation.navigation.groupsNav
import br.com.jwar.sharedbill.groups.presentation.ui.group_list.GROUP_LIST_ROUTE

@Composable
@ExperimentalAnimationApi
fun NavGraph(
    navController: NavHostController,
    startDestination: String = GROUP_LIST_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        accountNav(
            navController = navController,
            onNavigateToHome = {
                navController.navigate(GROUP_LIST_ROUTE) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        )
        groupsNav(
            navController = navController,
            onNavigateToAuth = {
                navController.navigate(AUTH_ROUTE)
            }
        )
    }
}



