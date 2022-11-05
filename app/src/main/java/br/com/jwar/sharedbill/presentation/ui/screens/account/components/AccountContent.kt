package br.com.jwar.sharedbill.presentation.ui.screens.account.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.account.AccountContract.State.*
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.verticalSpaceMedium

@Composable
fun AccountContent(
    state: State,
    onSignOutClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when(state) {
            is Loading -> LoadingContent()
            is Loaded -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UserInfo(state.user)
                    Spacer(modifier = Modifier.verticalSpaceMedium())
                    SignOutButton(onSignOutClick)
                }
            }
            is Error -> {
                Text(text = state.message)
                SignOutButton(onSignOutClick)
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
                UserUiModel.sample()
            ))
        }
    }
}
