package br.com.jwar.sharedbill.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.*
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountScreen
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthScreen
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsScreen
import br.com.jwar.sharedbill.presentation.ui.screens.group_edit.GroupEditScreen
import br.com.jwar.sharedbill.presentation.ui.screens.group_list.GroupListScreen
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

const val GROUP_ID_ARG = "groupId"

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
            createAuthScreenRoute(navController, snackbarHostState)
        }
        composable(route = Account.route) {
            createAccountScreenRoute(navController)
        }
        composable(route = GroupList.route) {
            createGroupListScreenRoute(navController, snackbarHostState)
        }
        composable(route = GroupDetails.route) { backStackEntry ->
            createGroupDetailsScreenRoute(navController, backStackEntry)
        }
        composable(route = GroupEdit.route) { backStackEntry ->
            createGroupEditScreenRoute(navController, backStackEntry, snackbarHostState)
        }
        composable(route = Payment.route) { backStackEntry ->
            createPaymentScreenRoute(navController, backStackEntry, snackbarHostState)
        }
    }
}

@Composable
private fun createPaymentScreenRoute(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState
) {
    PaymentScreen(
        navigateBack = { navController.popBackStack() },
        snackbarHostState = snackbarHostState,
        groupId = backStackEntry.getGroupId()
    )
}

@Composable
private fun createGroupEditScreenRoute(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState
) {
    GroupEditScreen(
        navigateBack = { navController.popBackStack() },
        snackbarHostState = snackbarHostState,
        groupId = backStackEntry.getGroupId()
    )
}

@Composable
private fun createGroupDetailsScreenRoute(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry
) {
    GroupDetailsScreen(
        navigateBack = { navController.popBackStack() },
        navigateToGroupEdit = { groupId ->
            navController.navigate(GroupEdit.createRoute(groupId))
        },
        navigateToNewPayment = { groupId ->
            navController.navigate(Payment.createRoute(groupId))
        },
        groupId = backStackEntry.getGroupId()
    )
}

@Composable
private fun createGroupListScreenRoute(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    GroupListScreen(
        snackbarHostState = snackbarHostState,
        navigateToAuth = {
            navController.popBackStack()
            navController.navigate(Auth.route)
        },
        navigateToGroupDetails = { groupId ->
            navController.navigate(GroupDetails.createRoute(groupId))
        },
        navigateToGroupEdit = { groupId ->
            navController.navigate(GroupEdit.createRoute(groupId))
        }
    )
}

@Composable
private fun createAccountScreenRoute(navController: NavHostController) {
    AccountScreen(
        navigateToAuth = {
            navController.navigate(Auth.route)
        }
    )
}

@Composable
private fun createAuthScreenRoute(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    AuthScreen(
        navigateToHome = {
            navController.navigate(GroupList.route) {
                navController.currentBackStackEntry?.destination?.route?.let {
                    popUpTo(it) {
                        inclusive = true
                    }
                }
            }
        },
        snackbarHostState = snackbarHostState
    )
}

private fun NavBackStackEntry.getGroupId() = arguments?.getString(GROUP_ID_ARG).orEmpty()