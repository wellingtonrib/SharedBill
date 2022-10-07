package br.com.jwar.sharedbill.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.Account
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.Auth
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupDetails
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupEdit
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupList
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.Payment
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountScreen
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthScreen
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsScreen
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditScreen
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListScreen
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
@ExperimentalAnimationApi
fun NavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = GroupList.route,
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
        composable(route = GroupDetails.route) { backStackEntry ->
            GroupDetailsScreen(navController = navController, groupId = backStackEntry.getGroupId())
        }
        composable(route = GroupEdit.route) { backStackEntry ->
            GroupEditScreen(navController = navController, groupId = backStackEntry.getGroupId())
        }
        composable(route = Payment.route) { backStackEntry ->
            PaymentScreen(navController = navController, groupId = backStackEntry.getGroupId())
        }
    }
}

private fun NavBackStackEntry.getGroupId() = arguments?.getString("groupId").orEmpty()