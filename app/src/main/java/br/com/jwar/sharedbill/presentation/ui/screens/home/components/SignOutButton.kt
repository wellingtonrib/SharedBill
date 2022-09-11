package br.com.jwar.sharedbill.presentation.ui.screens.home.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SignOutButton(
    onSignOutClick: () -> Unit
) {
    Button(onClick = onSignOutClick) {
        Text(text = "Logout")
    }
}