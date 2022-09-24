package br.com.jwar.sharedbill.presentation.ui.screens.auth.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SignInButton(onSignInClick: () -> Unit) {
    Button(
        onClick = onSignInClick
    ) {
        Text(
            text = "Sign with Google"
        )
    }
}