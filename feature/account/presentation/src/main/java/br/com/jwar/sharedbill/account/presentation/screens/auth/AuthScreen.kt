package br.com.jwar.sharedbill.account.presentation.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.account.presentation.screens.auth.AuthContract.State
import br.com.jwar.sharedbill.account.presentation.screens.auth.AuthContract.State.*
import br.com.jwar.sharedbill.account.presentation.screens.auth.components.Onboarding
import br.com.jwar.sharedbill.account.presentation.screens.auth.components.PrivacyPolicy
import br.com.jwar.sharedbill.account.presentation.screens.auth.components.SignInButton
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerLarge
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium

@Composable
fun AuthScreen(
    state: State,
    onSignInClick: () -> Unit = {}
) {
    when(state) {
        is Loading -> LoadingContent()
        is Idle -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.weight(0.6f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Onboarding(modifier = Modifier.paddingMedium())
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SignInButton(onSignInClick = onSignInClick)
                    VerticalSpacerLarge()
                    PrivacyPolicy()
                    VerticalSpacerLarge()
                }
            }
        }
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
