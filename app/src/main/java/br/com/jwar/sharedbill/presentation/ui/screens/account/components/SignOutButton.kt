@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.jwar.sharedbill.presentation.ui.screens.account.components

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun SignOutButton(
    onSignOutClick: () -> Unit = {}
) {
    Button(onClick = onSignOutClick) {
        Text(text = "Logout")
    }
}

@Preview
@Composable
fun PreviewSignInButton() {
    SharedBillTheme {
        Scaffold {
            SignOutButton()
        }
    }
}