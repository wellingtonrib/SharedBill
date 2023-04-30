package br.com.jwar.groups.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.jwar.groups.presentation.screens.group_details.GroupDetailsRoute
import br.com.jwar.groups.presentation.screens.group_edit.GroupEditRoute
import br.com.jwar.groups.presentation.screens.group_list.GROUP_LIST_ROUTE
import br.com.jwar.groups.presentation.screens.group_list.GroupListRoute
import br.com.jwar.groups.presentation.screens.payment.PaymentRoute
import com.google.accompanist.navigation.animation.composable

const val GROUP_ID_ARG = "groupId"
const val GROUP_DETAILS_ROUTE = "group_details/{$GROUP_ID_ARG}"
const val GROUP_EDIT_ROUTE = "group_edit/{$GROUP_ID_ARG}"
const val PAYMENT_ROUTE = "payment/{$GROUP_ID_ARG}"

fun NavGraphBuilder.groupsNav(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
) {
    composable(route = GROUP_LIST_ROUTE) {
        GroupListRoute(
            snackbarHostState = snackbarHostState,
            onNavigateToGroupDetails = { groupId ->
                navController.navigate(GROUP_DETAILS_ROUTE.bindGroupId(groupId))
            }
        ) { navController.popBackStack() }
    }
    composable(route = GROUP_DETAILS_ROUTE) { backStackEntry ->
        GroupDetailsRoute(
            groupId = backStackEntry.getGroupId(),
            onNavigateToGroupEdit = { groupId ->
                navController.navigate(GROUP_EDIT_ROUTE.bindGroupId(groupId))
            },
            onNavigateToNewPayment = { groupId ->
                navController.navigate(PAYMENT_ROUTE.bindGroupId(groupId))
            }
        ) { navController.popBackStack() }
    }
    composable(route = GROUP_EDIT_ROUTE) { backStackEntry ->
        GroupEditRoute(
            groupId = backStackEntry.getGroupId(),
            snackbarHostState = snackbarHostState,
            onNavigateToDetails = { groupId ->
                navController.popBackStack()
                navController.navigate(GROUP_DETAILS_ROUTE.bindGroupId(groupId))
            }
        ) { navController.popBackStack() }
    }
    composable(route = PAYMENT_ROUTE) { backStackEntry ->
        PaymentRoute(
            groupId = backStackEntry.getGroupId(),
        ) { navController.popBackStack() }
    }
}

private fun NavBackStackEntry.getGroupId() = arguments?.getString(GROUP_ID_ARG).orEmpty()

private fun String.bindGroupId(groupId: String) =
    this.replace("{${GROUP_ID_ARG}}", groupId)