package br.com.jwar.sharedbill.presentation.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme

@Composable
fun AppTopBar(
    navController: NavController,
    title: String = "",
    navigationIcon: @Composable () -> Unit = { BackNavigationIcon(navController) },
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = AppTheme.colors.primary,
            titleContentColor = AppTheme.colors.onPrimary,
            actionIconContentColor = AppTheme.colors.onPrimary,
            navigationIconContentColor = AppTheme.colors.onPrimary,
        ),
        navigationIcon = navigationIcon,
        actions = actions,
        title = { Text(text = title) }
    )
}

@Composable
fun BackNavigationIcon(
    navController: NavController,
) =
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.description_back))
    }

@Composable
fun CloseNavigationIcon(
    navController: NavController,
) =
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(Icons.Filled.Close, stringResource(id = R.string.description_close))
    }