package br.com.jwar.sharedbill.ui.account.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import br.com.jwar.sharedbill.ui.account.AccountRoute
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