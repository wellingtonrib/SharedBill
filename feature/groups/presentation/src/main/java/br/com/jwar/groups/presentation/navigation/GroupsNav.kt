package br.com.jwar.groups.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.groups.presentation.screens.group_details.GroupDetailsRoute
import br.com.jwar.groups.presentation.screens.group_edit.GroupEditRoute
import br.com.jwar.groups.presentation.screens.group_list.GROUP_LIST_ROUTE
import br.com.jwar.groups.presentation.screens.group_list.GroupListRoute
import br.com.jwar.groups.presentation.screens.payment.PaymentRoute
import androidx.navigation.compose.composable

const val GROUP_ID_ARG = "groupId"
const val PAYMENT_TYPE_ARG = "paymentType"
const val GROUP_DETAILS_ROUTE = "group_details/{$GROUP_ID_ARG}"
const val GROUP_EDIT_ROUTE = "group_edit/{$GROUP_ID_ARG}"
const val PAYMENT_ROUTE = "payment/{$GROUP_ID_ARG}/{$PAYMENT_TYPE_ARG}"

fun NavGraphBuilder.groupsNav(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    onNavigateAccount: () -> Unit,
) {
    composable(route = GROUP_LIST_ROUTE) {
        GroupListRoute(
            snackbarHostState = snackbarHostState,
            onNavigateToGroupDetails = { groupId ->
                navController.navigate(GROUP_DETAILS_ROUTE.bindGroupId(groupId))
            },
            onNavigateToGroupEdit = { groupId ->
                navController.navigate(GROUP_EDIT_ROUTE.bindGroupId(groupId))
            },
            onNavigateToAuth = {
                onNavigateAccount()
            }
        )
    }
    composable(route = GROUP_DETAILS_ROUTE) { backStackEntry ->
        GroupDetailsRoute(
            groupId = backStackEntry.getGroupId(),
            onNavigateToGroupEdit = { groupId ->
                navController.navigate(GROUP_EDIT_ROUTE.bindGroupId(groupId))
            },
            onNavigateToNewPayment = { groupId, paymentType ->
                navController.navigate(PAYMENT_ROUTE.bindGroupId(groupId).bindPaymentType(paymentType))
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
            paymentType = backStackEntry.getPaymentType(),
        ) { navController.popBackStack() }
    }
}

private fun NavBackStackEntry.getGroupId() = arguments?.getString(GROUP_ID_ARG).orEmpty()

private fun NavBackStackEntry.getPaymentType() =
    PaymentType.valueOf(arguments?.getString(PAYMENT_TYPE_ARG).orEmpty())

private fun String.bindGroupId(groupId: String) = this.replace("{$GROUP_ID_ARG}", groupId)

private fun String.bindPaymentType(paymentType: PaymentType) =
    this.replace("{$PAYMENT_TYPE_ARG}", paymentType.name)