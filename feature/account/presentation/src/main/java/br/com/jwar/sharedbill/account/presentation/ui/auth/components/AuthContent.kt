package br.com.jwar.sharedbill.account.presentation.ui.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerLarge
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium

@Composable
fun AuthContent(
    onSignInClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Onboarding(modifier = Modifier.paddingMedium())
        VerticalSpacerLarge()
        SignInButton(onSignInClick = onSignInClick)
        PrivacyPolicy(onPrivacyClick = onPrivacyClick)
        VerticalSpacerLarge()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthScreen() {
    SharedBillTheme {
        AuthContent()
    }
}