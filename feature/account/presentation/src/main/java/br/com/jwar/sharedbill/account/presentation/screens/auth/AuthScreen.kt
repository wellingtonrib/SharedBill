package br.com.jwar.sharedbill.account.presentation.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.account.presentation.screens.auth.AuthContract.State
import br.com.jwar.sharedbill.account.presentation.screens.auth.AuthContract.State.*
import br.com.jwar.sharedbill.account.presentation.screens.auth.components.SignInButton
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun AuthScreen(
    state: State,
    onSignInClick: () -> Unit = {}
) {
    when(state) {
        is Loading -> LoadingContent()
        is Idle -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SignInButton(onSignInClick)
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewAuthScreen() {
    SharedBillTheme {
        Scaffold {
            AuthScreen(
                state = Idle
            )
        }
    }
}
