package br.com.jwar.sharedbill.account.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.jwar.sharedbill.account.presentation.screens.AccountRoute
import com.google.accompanist.navigation.animation.composable

const val ACCOUNT_ROUTE = "account"

fun NavGraphBuilder.accountNav(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
) {
    composable(route = ACCOUNT_ROUTE) {
        AccountRoute(snackbarHostState = snackbarHostState)
    }
}