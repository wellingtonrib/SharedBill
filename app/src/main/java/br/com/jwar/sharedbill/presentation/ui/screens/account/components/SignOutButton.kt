package br.com.jwar.sharedbill.presentation.ui.screens.account.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SignOutButton(
    onSignOutClick: () -> Unit
) {
    Button(onClick = onSignOutClick) {
        Text(text = "Logout")
    }
}