package br.com.jwar.sharedbill.presentation.ui.screens.account.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract.State.Loaded
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract.State.Loading
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract.State.Error

import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent

@Composable
fun AccountContent(
    state: State,
    onSignOutClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(state) {
                is Loading ->
                    LoadingContent()
                is Loaded -> {
                    Text(text = "Welcome ${state.user.name}")
                    SignOutButton(onSignOutClick)
                }
                is Error -> {
                    Text(text = state.message)
                    SignOutButton(onSignOutClick)
                }
            }
        }
    }
}



@Preview
@Composable
fun PreviewAccountContent() {
    SharedBillTheme {
        Scaffold {
            AccountContent(state = Loaded(
                User(name = "User Name")
            ))
        }
    }
}
