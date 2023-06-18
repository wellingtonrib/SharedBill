package br.com.jwar.sharedbill.account.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.jwar.sharedbill.account.presentation.screens.account.AccountRoute
import br.com.jwar.sharedbill.account.presentation.screens.auth.AuthRoute
import com.google.accompanist.navigation.animation.composable

const val ACCOUNT_ROUTE = "account"
const val AUTH_ROUTE = "auth"

fun NavGraphBuilder.accountNav(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    onNavigateToHome: () -> Unit,
) {
    composable(route = ACCOUNT_ROUTE) {
        AccountRoute(
            snackbarHostState = snackbarHostState,
            onNavigateToAuth = {
                navController.navigate(AUTH_ROUTE)
            }
        )
    }
    composable(route = AUTH_ROUTE) {
        AuthRoute(
            snackbarHostState = snackbarHostState,
            onNavigateToHome = onNavigateToHome
        )
    }
}