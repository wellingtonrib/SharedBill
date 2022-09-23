package br.com.jwar.sharedbill.presentation.ui.screens.account.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract
import br.com.jwar.sharedbill.presentation.ui.widgets.ProgressBar

@Composable
fun AccountContent(
    state: AccountContract.State,
    onSignOutClick: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        when(state) {
            is AccountContract.State.Loading ->
                ProgressBar()
            is AccountContract.State.Loaded -> {
                Text(text = "Welcome ${state.user.name}")
                SignOutButton(onSignOutClick)
            }
            is AccountContract.State.Error -> {
                Text(text = state.message)
                SignOutButton(onSignOutClick)
            }
        }
    }
}
