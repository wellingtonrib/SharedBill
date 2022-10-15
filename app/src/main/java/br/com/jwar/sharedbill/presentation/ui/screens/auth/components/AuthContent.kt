package br.com.jwar.sharedbill.presentation.ui.screens.auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthContract
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent

@Composable
fun AuthContent(
    state: AuthContract.State,
    snackHostState: SnackbarHostState = SnackbarHostState(),
    onSignInClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (state.isAuthenticating) LoadingContent()
        else SignInButton(onSignInClick)
    }
    SnackbarHost(
        hostState = snackHostState,
        modifier = Modifier.fillMaxWidth().wrapContentHeight(Alignment.Bottom)
    )
}

@Preview
@Composable
fun PreviewAuthContent() {
    SharedBillTheme {
        Scaffold {
            AuthContent(state = AuthContract.State(isAuthenticating = false))
        }
    }
}

