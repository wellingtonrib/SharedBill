package br.com.jwar.sharedbill.account.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountRoute
import br.com.jwar.sharedbill.account.presentation.ui.auth.AuthRoute

const val ACCOUNT_ROUTE = "account"
const val AUTH_ROUTE = "auth"

fun NavGraphBuilder.accountNav(
    navController: NavHostController,
    onNavigateToHome: () -> Unit,
) {
    composable(route = ACCOUNT_ROUTE) {
        AccountRoute(
            onNavigateToAuth = {
                navController.navigate(AUTH_ROUTE) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        )
    }
    composable(route = AUTH_ROUTE) {
        AuthRoute(
            onNavigateToHome = onNavigateToHome
        )
    }
}
