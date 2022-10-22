package br.com.jwar.sharedbill.presentation.ui.screens.auth.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthContract
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun AuthContent(
    state: AuthContract.State,
    onSignInClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (state.isAuthenticating) LoadingContent()
        else SignInButton(onSignInClick)
    }
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

