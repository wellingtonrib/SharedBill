package br.com.jwar.sharedbill.presentation.ui.screens.account.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import br.com.jwar.sharedbill.presentation.ui.theme.VerticalSpacerMedium

@Composable
fun AccountScreen(
    state: State,
    onSignOutClick: () -> Unit = {}
) {
    when(state) {
        is Loading -> LoadingContent()
        is Loaded -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                UserInfo(state.uiModel)
                VerticalSpacerMedium()
                SignOutButton(onSignOutClick)
            }
        }
        is Error -> {
            Text(text = state.message)
            SignOutButton(onSignOutClick)
        }
    }
}


@Preview
@Composable
fun PreviewAccountContent() {
    SharedBillTheme {
        Scaffold {
            AccountScreen(state = Loaded(
                UserUiModel.sample()
            ))
        }
    }
}
