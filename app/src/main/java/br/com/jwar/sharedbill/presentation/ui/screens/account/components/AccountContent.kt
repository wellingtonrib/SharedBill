package br.com.jwar.sharedbill.presentation.ui.screens.account.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.UserUiModel
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            when(state) {
                is Loading -> LoadingContent()
                is Loaded -> {
                    Text(text = stringResource(R.string.message_welcome, state.user.name))
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
                UserUiModel.sample()
            ))
        }
    }
}
