package br.com.jwar.sharedbill.presentation.ui.screens.auth.components

import androidx.compose.material.Button
import androidx.compose.material.Text
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