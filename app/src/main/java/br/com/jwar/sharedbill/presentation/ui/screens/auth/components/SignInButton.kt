@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.jwar.sharedbill.presentation.ui.screens.auth.components

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun SignInButton(
    onSignInClick: () -> Unit = {}
) {
    Button(
        onClick = onSignInClick
    ) {
        Text(
            text = "Sign with Google"
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