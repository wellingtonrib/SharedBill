package br.com.jwar.sharedbill.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.*
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountScreen
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthScreen
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupCreateScreen
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsScreen
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
fun NavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Account.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = Auth.route) {
            AuthScreen(navController = navController)
        }
        composable(route = Account.route) {
            AccountScreen(navController = navController)
        }
        composable(route = GroupList.route) {
            GroupListScreen(navController = navController)
        }
        composable(
            route = GroupDetails.route
        ) { backStackEntry ->
            GroupDetailsScreen(
                navController = navController,
                groupId = backStackEntry.arguments?.getString("groupId").orEmpty()
            )
        }
        composable(route = GroupCreate.route) {
            GroupCreateScreen(navController = navController)
        }
    }
}