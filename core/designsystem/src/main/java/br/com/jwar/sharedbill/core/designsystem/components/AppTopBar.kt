package br.com.jwar.sharedbill.core.designsystem.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun AppTopBar(
    title: String = "",
    navigationBack: () -> Unit = {},
    navigationIcon: @Composable () -> Unit = { BackNavigationIcon(navigationBack) },
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        navigationIcon = navigationIcon,
        actions = actions,
        title = { Title(title) }
    )
}

@Composable
fun BackNavigationIcon(
    navigateBack: () -> Unit,
) =
    IconButton(onClick = { navigateBack() }) {
        Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.description_back))
    }

@Composable
fun CloseNavigationIcon(
    navigateBack: () -> Unit,
) =
    IconButton(onClick = { navigateBack() }) {
        Icon(Icons.Filled.Close, stringResource(id = R.string.description_close))
    }

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PreviewAppTopBar() {
    SharedBillTheme {
        Scaffold {
            AppTopBar(
                title = "Title",
            )
        }
    }
}