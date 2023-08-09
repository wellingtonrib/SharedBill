package br.com.jwar.sharedbill.account.presentation.ui.auth

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.account.presentation.ui.auth.AuthContract.State
import br.com.jwar.sharedbill.account.presentation.ui.auth.AuthContract.State.*
import br.com.jwar.sharedbill.account.presentation.ui.auth.components.AuthContent
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun AuthScreen(
    state: State,
    onSignInClick: () -> Unit = {}
) {
    when(state) {
        is Loading -> LoadingContent()
        is Idle -> AuthContent(onSignInClick)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(device = "id:pixel_2")
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
