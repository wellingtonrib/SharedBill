package br.com.jwar.sharedbill.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.navigation.AppDestinations.ACCOUNT_ROUTE
import br.com.jwar.sharedbill.presentation.navigation.AppDestinations.GROUP_LIST_ROUTE

enum class AppBottomBarItem(val title: Int, val icon: Int, val route: String) {
    Groups(R.string.label_groups, R.drawable.ic_baseline_groups_24, GROUP_LIST_ROUTE),
    Account(R.string.label_account, R.drawable.ic_baseline_account_circle_24, ACCOUNT_ROUTE)
}

@Composable
fun AppBottomBar(navController: NavController) {
    val bottomBarItems = AppBottomBarItem.values()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomBarItems.map { it.route }

    if (!showBottomBar) return

    NavigationBar {
        bottomBarItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = stringResource(id = item.title)) },
                label = { Text(text = stringResource(id = item.title)) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}