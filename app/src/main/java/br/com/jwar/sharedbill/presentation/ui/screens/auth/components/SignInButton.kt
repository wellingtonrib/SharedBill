package br.com.jwar.sharedbill.presentation.ui.screens.auth.components

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun SignInButton(
    onSignInClick: () -> Unit = {}
) {
    Button(
        onClick = onSignInClick
    ) {
        Text(
            text = stringResource(R.string.label_sign_in)
        )
    }
}

@Preview
@Composable
fun PreviewSignInButton() {
    SharedBillTheme {
        Scaffold {
            SignInButton()
        }
    }
}