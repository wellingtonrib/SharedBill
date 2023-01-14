package br.com.jwar.sharedbill.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import br.com.jwar.sharedbill.presentation.navigation.AppDestinationsArgs.GROUP_ID_ARG
import br.com.jwar.sharedbill.presentation.navigation.AppRoute.*
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountRoute
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthRoute
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsRoute
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditRoute
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListRoute
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentRoute
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
@ExperimentalAnimationApi
fun NavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = GroupList.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = Auth.route) {
            createAuthRoute(navController, snackbarHostState)
        }
        composable(route = Account.route) {
            createAccountRoute(navController)
        }
        composable(route = GroupList.route) {
            createGroupListRoute(navController, snackbarHostState)
        }
        composable(route = GroupDetails.route) { backStackEntry ->
            createGroupDetailsRoute(navController, backStackEntry)
        }
        composable(route = GroupEdit.route) { backStackEntry ->
            createGroupEditRoute(navController, backStackEntry, snackbarHostState)
        }
        composable(route = Payment.route) { backStackEntry ->
            createPaymentRoute(navController, backStackEntry)
        }
    }
}

@Composable
private fun createAuthRoute(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    AuthRoute(
        snackbarHostState = snackbarHostState,
    ) {
        navController.navigate(GroupList.route) {
            navController.currentBackStackEntry?.destination?.route?.let {
                popUpTo(it) {
                    inclusive = true
                }
            }
        }
    }
}

@Composable
private fun createAccountRoute(navController: NavHostController) {
    AccountRoute(
        onNavigateToAuth = { navController.navigate(Auth.route) }
    )
}

@Composable
private fun createGroupListRoute(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    GroupListRoute(
        snackbarHostState = snackbarHostState,
        onNavigateToGroupDetails = { groupId ->
            navController.navigate(GroupDetails.createRoute(groupId))
        }
    ) {
        navController.popBackStack()
        navController.navigate(Auth.route)
    }
}

@Composable
private fun createGroupDetailsRoute(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry
) {
    GroupDetailsRoute(
        groupId = backStackEntry.getGroupId(),
        onNavigateToGroupEdit = { groupId ->
            navController.navigate(GroupEdit.createRoute(groupId))
        },
        onNavigateToNewPayment = { groupId ->
            navController.navigate(Payment.createRoute(groupId))
        }
    ) { navController.popBackStack() }
}

@Composable
private fun createGroupEditRoute(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState
) {
    GroupEditRoute(
        groupId = backStackEntry.getGroupId(),
        snackbarHostState = snackbarHostState,
        onNavigateToDetails = { groupId ->
            navController.popBackStack()
            navController.navigate(GroupDetails.createRoute(groupId))
        }
    ) { navController.popBackStack() }
}

@Composable
private fun createPaymentRoute(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
) {
    PaymentRoute(
        groupId = backStackEntry.getGroupId(),
    ) { navController.popBackStack() }
}

private fun NavBackStackEntry.getGroupId() = arguments?.getString(GROUP_ID_ARG).orEmpty()
