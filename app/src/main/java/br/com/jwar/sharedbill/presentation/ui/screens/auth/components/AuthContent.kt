package br.com.jwar.sharedbill.presentation.ui.screens.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.presentation.ui.screens.auth.AuthContract
import br.com.jwar.sharedbill.presentation.ui.widgets.ProgressBar

@Composable
fun AuthContent(
    state: AuthContract.State,
    snackHostState: SnackbarHostState,
    onSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        if (state.isAuthenticating) ProgressBar()
        else SignInButton(onSignInClick)
    }
    SnackbarHost(
        hostState = snackHostState,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}

