package br.com.jwar.sharedbill.account.presentation.ui.account

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract.State
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract.State.*
import br.com.jwar.sharedbill.account.presentation.ui.account.components.AccountForm
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun AccountScreen(
    state: State,
    onSignOutClick: () -> Unit = {},
    onSupportClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onAboutDismiss: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onRateUsClick: () -> Unit = {},
) {
    AccountForm(
        state = state,
        onSignOutClick = onSignOutClick,
        onSupportClick = onSupportClick,
        onAboutClick = onAboutClick,
        onAboutDismiss = onAboutDismiss,
        onTermsClick = onTermsClick,
        onPrivacyClick = onPrivacyClick,
        onRateUsClick = onRateUsClick,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewAccountContent() {
    SharedBillTheme {
        Scaffold {
            AccountScreen(
                state = State(uiModel = UserUiModel.sample()),
            )
        }
    }
}
