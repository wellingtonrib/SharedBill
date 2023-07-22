package br.com.jwar.sharedbill.account.presentation.screens.account

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.account.presentation.screens.account.AccountContract.State
import br.com.jwar.sharedbill.account.presentation.screens.account.AccountContract.State.*
import br.com.jwar.sharedbill.account.presentation.screens.account.components.AccountForm
import br.com.jwar.sharedbill.core.designsystem.components.ErrorContent
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun AccountScreen(
    state: State,
    onSignOutClick: () -> Unit = {},
) {
    when(state) {
        is Loading -> LoadingContent()
        is Loaded -> AccountForm(state = state, onSignOutClick = onSignOutClick)
        is Error -> ErrorContent(message = state.message, onAction = { onSignOutClick() })
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewAccountContent() {
    SharedBillTheme {
        Scaffold {
            AccountScreen(
                state = Loaded(
                    UserUiModel.sample()
                )
            )
        }
    }
}
