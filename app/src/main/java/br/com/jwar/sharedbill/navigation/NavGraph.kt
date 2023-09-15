package br.com.jwar.sharedbill.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.jwar.groups.presentation.navigation.groupsNav
import br.com.jwar.groups.presentation.ui.group_list.GROUP_LIST_ROUTE
import br.com.jwar.sharedbill.account.presentation.navigation.ACCOUNT_ROUTE
import br.com.jwar.sharedbill.account.presentation.navigation.accountNav
import com.google.accompanist.navigation.animation.AnimatedNavHost

@Composable
@ExperimentalAnimationApi
fun NavGraph(
    navController: NavHostController,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = GROUP_LIST_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        accountNav(
            navController = navController,
            onNavigateToHome = { navController.navigate(GROUP_LIST_ROUTE) }
        )
        groupsNav(
            navController = navController,
            onNavigateAccount = { navController.navigate(ACCOUNT_ROUTE) }
        )
    }
}



