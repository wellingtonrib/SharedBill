package br.com.jwar.sharedbill.account.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountRoute
import br.com.jwar.sharedbill.account.presentation.ui.auth.AuthRoute
import androidx.navigation.compose.composable

const val ACCOUNT_ROUTE = "account"
const val AUTH_ROUTE = "auth"

fun NavGraphBuilder.accountNav(
    navController: NavHostController,
    onNavigateToHome: () -> Unit,
) {
    composable(route = ACCOUNT_ROUTE) {
        AccountRoute(
            onNavigateToAuth = {
                navController.navigate(AUTH_ROUTE)
            }
        )
    }
    composable(route = AUTH_ROUTE) {
        AuthRoute(
            onNavigateToHome = onNavigateToHome
        )
    }
}