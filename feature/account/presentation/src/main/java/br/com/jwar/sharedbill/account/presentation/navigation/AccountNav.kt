package br.com.jwar.sharedbill.account.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import br.com.jwar.sharedbill.account.presentation.AccountRoute
import com.google.accompanist.navigation.animation.composable

const val ACCOUNT_ROUTE = "account"

fun NavGraphBuilder.accountScreen(
    snackbarHostState: SnackbarHostState,
) {
    composable(route = ACCOUNT_ROUTE) {
        AccountRoute(
            snackbarHostState = snackbarHostState,
        )
    }
}